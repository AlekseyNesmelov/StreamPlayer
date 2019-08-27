package ru.leksiinesm.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.leksiinesm.notification.NotificationBuilder
import ru.leksiinesm.notification.NotificationBuilderImpl
import ru.leksiinesm.playerlib.focus.AudioFocusManager
import ru.leksiinesm.playerlib.focus.AudioFocusManagerImpl
import ru.leksiinesm.playerlib.focus.AudioFocusState
import ru.leksiinesm.playerlib.player.StreamPlayer
import ru.leksiinesm.playerlib.player.StreamPlayerImpl

class MediaPlayerService : Service() {

    private lateinit var audioFocusManager: AudioFocusManager
    private lateinit var player: StreamPlayer
    private lateinit var notificationBuilder: NotificationBuilder

    private val binder = LocalBinder()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var started: Boolean = false

    override fun onCreate() {
        super.onCreate()
        notificationBuilder = NotificationBuilderImpl()
        audioFocusManager = AudioFocusManagerImpl(getSystemService(Context.AUDIO_SERVICE) as AudioManager)
        player = StreamPlayerImpl(MediaPlayer())

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(FOREGROUND_ID, notificationBuilder.build(applicationContext))
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?) = binder

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    @Synchronized
    fun start() {
        started = true
        audioFocusManager.start()
        compositeDisposable.add(
            audioFocusManager.focusChangeObservable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ state ->
                    if (state == AudioFocusState.IN_FOCUS) {
                        player.start()
                    } else {
                        player.stop()
                    }
                }, { e -> Log.e(TAG, "", e) })
        )
    }

    @Synchronized
    fun stop() {
        audioFocusManager.release()
        player.stop()
        started = false
        stopSelf()
    }

    @Synchronized
    fun isStarted() = started

    @Synchronized
    fun setSource(source: String) {
        player.setSource(source)
    }

    inner class LocalBinder : Binder() {
        fun getService(): MediaPlayerService = this@MediaPlayerService
    }

    companion object {
        private const val TAG = "MediaPlayerService"
        private const val FOREGROUND_ID = 1222
    }
}