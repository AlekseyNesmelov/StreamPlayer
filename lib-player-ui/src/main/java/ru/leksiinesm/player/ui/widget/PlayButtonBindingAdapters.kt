package ru.leksiinesm.player.ui.widget

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
    playButton.setPlaying(showPlaying)
}