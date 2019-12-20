package ru.leksiinesm.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.leksiinesm.core.viewmodel.DisposableViewModel
import ru.leksiinesm.player.domain.interactor.PlayerInteractor
import ru.leksiinesm.storage.data.storage.DataStorage

/**
 * TODO draft
 *
 * @author Alexey Nesmelov
 */
class PlayerViewModel(
    private val interactor: PlayerInteractor,
    private val storage: DataStorage
) : DisposableViewModel() {

    private val isPlayingMutable: MutableLiveData<Boolean> = MutableLiveData(storage.isPlaying)
    private val isLoadingMutable: MutableLiveData<Boolean> = MutableLiveData(false)

    fun clickPlay() {
        if (storage.isPlaying) {
            interactor.stop()
            isPlayingMutable.postValue(false)
        } else {
            interactor.play()
            isPlayingMutable.postValue(true)
        }
    }

    fun isPlaying(): LiveData<Boolean> = isPlayingMutable

    fun isLoading(): LiveData<Boolean> = isLoadingMutable

    companion object {
        const val TAG = "PlayerViewModel"
    }
}