package ru.leksiinesm.core.viewmodel

import ru.leksiinesm.core.rx.disposables.RxDisposables

open class DisposableViewModel(private val rxDisposables: RxDisposables) : BaseViewModel() {

    override fun onCleared() {
        rxDisposables.container.dispose()
    }
}