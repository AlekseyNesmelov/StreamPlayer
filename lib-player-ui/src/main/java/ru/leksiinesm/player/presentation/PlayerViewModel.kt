package ru.leksiinesm.player.presentation

import ru.leksiinesm.core.viewmodel.BaseViewModel
import ru.leksiinesm.player.domain.PlayerInteractor

/**
 * TODO draft
 *
 * @author Alexey Nesmelov
 */
class PlayerViewModel(private val interactor: PlayerInteractor) : BaseViewModel() {

    fun clickPlay() = interactor.clickPlay()
}