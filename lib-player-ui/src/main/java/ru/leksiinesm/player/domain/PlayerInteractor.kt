package ru.leksiinesm.player.domain

/**
 * Interactor for stream playing.
 *
 * @author Alexey Nesmelov
 */
interface PlayerInteractor {

    /**
     * TODO split to start/stop
     */
    fun clickPlay()

    fun isPlaying(): Boolean
}