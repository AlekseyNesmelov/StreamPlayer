package ru.leksiinesm.player.domain.connector

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.annotation.UiThread
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ru.leksiinesm.playerlib.IMediaService
import ru.leksiinesm.playerlib.service.MediaPlayerService

class ServiceConnector(private val context: Context) {

    private var serviceConnection = MediaPlayerServiceConnection()
    private var playerService: IMediaService? = null
    private var isBinding = false
    private val isBound: Boolean
        get() = playerService != null

    private var pendingAction: (IMediaService) -> Unit = {}

    private val isPlayingSubject = BehaviorSubject.createDefault(false)

    @UiThread
    fun usePlayer(action: (IMediaService) -> Unit) {
        if (isBound) {
            action(playerService!!)
        } else {
            pendingAction = action
            bindService()
        }
    }

    @UiThread
    fun isPlayingObservable(): Observable<Boolean> = isPlayingSubject.hide()

    @UiThread
    fun requestIsPlaying() {
        if (isBound) {
            postPlayingState()
        } else {
            bindService()
        }
    }

    @UiThread
    fun release() = unbindService()

    @UiThread
    private fun bindService() {
        if (!isBound && !isBinding) {
            isBinding = true
            Intent(context, MediaPlayerService::class.java).also { intent ->
                context.startService(intent)
                context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    @UiThread
    private fun unbindService() {
        if (isBound) {
            context.unbindService(serviceConnection)
            playerService = null
        }
    }

    private fun postPlayingState() {
        isPlayingSubject.onNext(playerService!!.isPlaying)
    }

    inner class MediaPlayerServiceConnection : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            playerService = IMediaService.Stub.asInterface(service)
            pendingAction(playerService!!)
            isBinding = false
            postPlayingState()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            playerService = null
            isBinding = false
        }
    }
}