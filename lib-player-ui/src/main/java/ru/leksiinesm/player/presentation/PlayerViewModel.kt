package ru.leksiinesm.player.presentation

import ru.leksiinesm.core.viewmodel.BaseViewModel
import ru.leksiinesm.player.domain.PlayerInteractor

class PlayerViewModel(private val interactor: PlayerInteractor) : BaseViewModel() {

    fun clickPlay() = interactor.clickPlay()
}