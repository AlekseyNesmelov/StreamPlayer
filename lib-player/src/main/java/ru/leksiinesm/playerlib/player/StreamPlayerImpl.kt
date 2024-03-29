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
import ru.leksiinesm.storage.data.storage.DataStorageImpl
import ru.leksiinesm.storage.data.storage.MutableDataStorage

/**
 * Default implementation of [StreamPlayer].
 *
 * @author Alexey Nesmelov
 */
// TODO: draft
class StreamPlayerImpl @JvmOverloads constructor(
    private val context: Context,
    private val playerDataStorage: MutableDataStorage = DataStorageImpl(context),
    private val exoPlayer: SimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context),
    private val dataSourceFactory: DefaultDataSourceFactory = DefaultDataSourceFactory(
        context,
        Util.getUserAgent(context, APP_NAME)
    )
) : StreamPlayer {
    private var dataSource: String? = null
    private val dataStorage: MutableDataStorage

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .build()
        exoPlayer.setAudioAttributes(audioAttributes, true)
        dataStorage = DataStorageImpl(context)
    }

    @Synchronized
    override fun start(url: String) {
        dataSource = url
        val mediaSource = createMediaSource()
        exoPlayer.prepare(mediaSource)
        exoPlayer.playWhenReady = true
        playerDataStorage.isPlaying = true
    }

    @Synchronized
    override fun start() {
        dataSource?.let { start(it) }
    }

    @Synchronized
    override fun stop() {
        exoPlayer.stop()
        playerDataStorage.isPlaying = false
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