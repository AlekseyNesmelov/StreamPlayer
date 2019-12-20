package ru.leksiinesm.player.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.leksiinesm.player.domain.interactor.PlayerInteractor
import ru.leksiinesm.player.presentation.PlayerViewModel
import ru.leksiinesm.storage.data.storage.DataStorage

/**
 * Factory for [PlayerViewModel].
 *
 * @param playerInteractor interactor of stream player.
 *
 * @author Alexey Nesmelov
 */
class PlayerViewModelFactory(
    private val playerInteractor: PlayerInteractor,
    private val storage: DataStorage
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlayerViewModel(playerInteractor, storage) as T
    }
}