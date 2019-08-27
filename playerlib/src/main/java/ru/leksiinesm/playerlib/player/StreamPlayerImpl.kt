package ru.leksiinesm.playerlib.player

import android.media.AudioManager
import android.media.MediaPlayer

class StreamPlayerImpl(private val mediaPlayer: MediaPlayer) : StreamPlayer {

    private var dataSource: String? = null
    private var isPreparing: Boolean = false

    init {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
    }

    @Synchronized
    override fun start() {
        if (mediaPlayer.isPlaying || isPreparing) {
            return
        }
        isPreparing = true
        dataSource?.let {
            mediaPlayer.setDataSource(it)
        }
        mediaPlayer.apply {
            prepareAsync()
            setOnPreparedListener {
                start()
                isPreparing = false
            }
        }
    }

    @Synchronized
    override fun stop() {
        isPreparing = false
        mediaPlayer.stop()
        mediaPlayer.reset()
    }

    override fun setSource(url: String) {
        dataSource = url
    }
}