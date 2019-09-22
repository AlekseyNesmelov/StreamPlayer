package ru.leksiinesm.playerlib.player

/**
 * Audio stream player
 *
 * @author Alexey Nesmelov
 */
interface StreamPlayer {

    /**
     * Start playing audio stream from [url]
     */
    fun start(url: String)

    /**
     * Start playing audio stream using last used url
     */
    fun start()

    /**
     * Stop playing audio stream
     */
    fun stop()

    /**
     * Release player resources
     */
    fun release()
}