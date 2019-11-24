package ru.leksiinesm.player.domain.interactor

import io.reactivex.Observable

/**
 * Interactor for stream playing.
 *
 * @author Alexey Nesmelov
 */
interface PlayerInteractor {

    /**
     * Start playing stream
     */
    fun play()

    /**
     * Stop playing stream
     */
    fun stop()

    /**
     * Check if player is playing
     */
    fun isPlaying(): Observable<Boolean>
}