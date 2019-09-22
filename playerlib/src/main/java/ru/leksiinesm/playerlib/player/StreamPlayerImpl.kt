package ru.leksiinesm.playerlib.player

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


/**
 * Default implementation of [StreamPlayer]
 *
 * @author Alexey Nesmelov
 */
class StreamPlayerImpl(context: Context) : StreamPlayer {

    private val exoPlayer: SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context)
    private val dataSourceFactory = DefaultDataSourceFactory(
        context,
        Util.getUserAgent(context, APP_NAME)
    )
    private var dataSource: String? = null

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .build()
        exoPlayer.setAudioAttributes(audioAttributes, true)
    }

    @Synchronized
    override fun start(url: String) {
        dataSource = url
        val mediaSource = createMediaSource()
        exoPlayer.prepare(mediaSource)
        exoPlayer.playWhenReady = true
    }

    @Synchronized
    override fun start() {
        dataSource?.let { start(it) }
    }

    @Synchronized
    override fun stop() {
        exoPlayer.stop()
    }

    @Synchronized
    override fun release() {
        exoPlayer.release()
    }

    private fun createMediaSource(): MediaSource {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(dataSource))
    }

    companion object {
        const val APP_NAME = "ru.leksiinesm.streamplayer"
    }
}