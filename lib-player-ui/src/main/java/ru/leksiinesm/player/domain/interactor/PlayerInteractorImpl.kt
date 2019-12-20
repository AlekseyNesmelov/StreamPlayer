package ru.leksiinesm.player.domain.interactor

import android.content.Context
import android.content.Intent
import android.os.Build
import io.reactivex.Observable
import ru.leksiinesm.playerlib.service.COMMAND
import ru.leksiinesm.playerlib.service.MediaPlayerService
import ru.leksiinesm.playerlib.service.PLAY
import ru.leksiinesm.playerlib.service.SOURCE
import ru.leksiinesm.playerlib.service.STOP

/**
 * Default implementation of [PlayerInteractor]
 */
class PlayerInteractorImpl(private val context: Context) : PlayerInteractor {

    override fun play() {
        startService(
            Intent(context, MediaPlayerService::class.java).apply {
                putExtra(COMMAND, PLAY)
                putExtra(SOURCE, COMEDY_RADIO_URL)
            })
    }

    override fun stop() {
        startService(
            Intent(context, MediaPlayerService::class.java).apply {
                putExtra(COMMAND, STOP)
            })
    }

    override fun isPlaying(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun startService(intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    companion object {
        const val COMEDY_RADIO_URL = "http://ic4.101.ru:8000/a202"
    }
}