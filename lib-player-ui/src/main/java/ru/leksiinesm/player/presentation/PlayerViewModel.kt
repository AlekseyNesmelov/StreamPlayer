package ru.leksiinesm.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.leksiinesm.core.viewmodel.BaseViewModel
import ru.leksiinesm.player.domain.PlayerInteractor

/**
 * TODO draft
 *
 * @author Alexey Nesmelov
 */
class PlayerViewModel(private val interactor: PlayerInteractor) : BaseViewModel() {

    private val isPlayingMutable: MutableLiveData<Boolean> = MutableLiveData(interactor.isPlaying())
    private val isLoadingMutable: MutableLiveData<Boolean> = MutableLiveData(false)

    fun clickPlay() {
        interactor.clickPlay()
        val cur = isLoadingMutable.value
        isLoadingMutable.value = cur?.not()
    }

    fun isPlaying(): LiveData<Boolean> = isPlayingMutable

    fun isLoading(): LiveData<Boolean> = isLoadingMutable
}