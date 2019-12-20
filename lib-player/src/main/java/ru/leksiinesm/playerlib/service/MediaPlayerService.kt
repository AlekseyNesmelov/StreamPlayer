package ru.leksiinesm.playerlib.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.leksiinesm.notification.NotificationBuilder
import ru.leksiinesm.playerlib.di.DaggerPlayerComponent
import ru.leksiinesm.playerlib.di.PlayerModule
import ru.leksiinesm.playerlib.player.StreamPlayer
import javax.inject.Inject

/**
 * Service for music stream playing
 */
class MediaPlayerService : Service() {

    @Inject
    lateinit var notificationBuilder: NotificationBuilder
    @Inject
    lateinit var player: StreamPlayer

    override fun onCreate() {
        super.onCreate()
        DaggerPlayerComponent.builder()
            .playerModule(PlayerModule(application))
            .build()
            .inject(this)
        startForeground(FOREGROUND_ID, notificationBuilder.build(applicationContext))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getIntExtra(COMMAND, DO_NOTHING)) {
            PLAY -> {
                intent.getStringExtra(SOURCE)?.let {
                    player.start(it)
                }
            }
            STOP -> {
                stopSelf()
                stopForeground(true)
            }
            DO_NOTHING -> {
                // do nothing
            }
            else -> throw IllegalStateException("Unsupported operation")
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        player.stop()
        player.release()
        super.onDestroy()
    }

    companion object {
        private const val FOREGROUND_ID = 1222
    }
}
