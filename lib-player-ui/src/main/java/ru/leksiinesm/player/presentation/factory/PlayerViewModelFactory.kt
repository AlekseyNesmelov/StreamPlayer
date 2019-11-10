package ru.leksiinesm.player.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.leksiinesm.player.domain.PlayerInteractor
import ru.leksiinesm.player.presentation.PlayerViewModel

/**
 * Factory for [PlayerViewModel].
 *
 * @param playerInteractor interactor of stream player.
 *
 * @author Alexey Nesmelov
 */
class PlayerViewModelFactory(private val playerInteractor: PlayerInteractor) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlayerViewModel(playerInteractor) as T
    }
}