package ru.leksiinesm.player.domain.interactor

import android.content.Context
import io.reactivex.Observable
import ru.leksiinesm.player.domain.connector.ServiceConnector

/**
 * Default implementation of [PlayerInteractor]
 */
class PlayerInteractorImpl(context: Context) : PlayerInteractor {

    private val serviceConnector = ServiceConnector(context)

    override fun play() {
        serviceConnector.usePlayer { it.start(COMEDY_RADIO_URL) }
    }

    override fun stop() {
        serviceConnector.usePlayer { it.stop() }
    }

    override fun isPlaying(): Observable<Boolean> = serviceConnector.isPlayingObservable()

    companion object {
        private const val COMEDY_RADIO_URL = "http://ic4.101.ru:8000/a202"
    }
}