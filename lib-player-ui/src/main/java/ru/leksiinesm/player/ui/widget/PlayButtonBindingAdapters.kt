package ru.leksiinesm.player.ui.widget

import android.util.Log
import androidx.databinding.BindingAdapter

@BindingAdapter("isLoading")
fun showProgress(playButton: PlayButton, showProgress: Boolean) {
    if (showProgress) {
        playButton.showProgress()
    } else {
        playButton.hideProgress()
    }
}

@BindingAdapter("isPlaying")
fun showPlaying(playButton: PlayButton, showPlaying: Boolean) {
    Log.d("BindingAdapter", "showPlaying: $showPlaying")
    playButton.setPlaying(showPlaying)
}