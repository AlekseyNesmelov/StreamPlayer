package ru.leksiinesm.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.leksiinesm.core.rx.schedullers.RxSchedulers
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
    rxSchedulers: RxSchedulers,
    private val storage: DataStorage
) : DisposableViewModel() {

    private val isPlayingMutable: MutableLiveData<Boolean> = MutableLiveData(storage.isPlaying)
    private val isLoadingMutable: MutableLiveData<Boolean> = MutableLiveData(false)

    fun clickPlay() {
        if (storage.isPlaying) {
            interactor.stop()
        } else {
            interactor.play()
        }
    }

    fun isPlaying(): LiveData<Boolean> = isPlayingMutable

    fun isLoading(): LiveData<Boolean> = isLoadingMutable

    companion object {
        const val TAG = "PlayerViewModel"
    }
}