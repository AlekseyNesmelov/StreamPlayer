package ru.leksiinesm.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import ru.leksiinesm.notification.NotificationBuilderImpl
import ru.leksiinesm.playerlib.player.StreamPlayer
import ru.leksiinesm.playerlib.player.StreamPlayerImpl

class MediaPlayerService : Service() {

    private val notificationBuilder = NotificationBuilderImpl()
    private val binder = LocalBinder()

    private lateinit var player: StreamPlayer

    private var started: Boolean = false

    override fun onCreate() {
        super.onCreate()
        player = StreamPlayerImpl(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(FOREGROUND_ID, notificationBuilder.build(applicationContext))
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?) = binder

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    @Synchronized
    fun start(source: String) {
        started = true
        player.start(source)
    }

    @Synchronized
    fun stop() {
        player.stop()
        started = false
        stopSelf()
    }

    @Synchronized
    fun isStarted() = started

    inner class LocalBinder : Binder() {
        fun getService(): MediaPlayerService = this@MediaPlayerService
    }

    companion object {
        private const val FOREGROUND_ID = 1222
    }
}