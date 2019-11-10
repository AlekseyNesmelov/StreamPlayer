package ru.leksiinesm.player.domain

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import ru.leksiinesm.playerlib.IMediaService
import ru.leksiinesm.playerlib.service.MediaPlayerService

//TODO draft
class PlayerInteractorImpl(private val context: Context): PlayerInteractor {

    private var serviceConnection = MediaPlayerServiceConnection()
    private var playerService: IMediaService? = null

    override fun clickPlay() {
        if (playerService != null) {
            triggerPlay()
        } else {
            Intent(context, MediaPlayerService::class.java).also { intent ->
                context.startService(intent)
                context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    inner class MediaPlayerServiceConnection : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            playerService = IMediaService.Stub.asInterface(service)
            triggerPlay()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            playerService = null
        }
    }

    private fun triggerPlay() {
        if (playerService == null) {
            return
        }
        if (playerService!!.isPlaying) {
            playerService?.stop()
            unbindService()
        } else {
            playerService?.start(COMEDY_RADIO_URL)
        }
    }

    private fun unbindService() {
        if (playerService != null) {
            context.unbindService(serviceConnection)
            playerService = null
        }
    }

    companion object {
        private const val COMEDY_RADIO_URL = "http://ic4.101.ru:8000/a202"
    }
}