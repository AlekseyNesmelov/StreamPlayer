package ru.leksiinesm.core.viewmodel

import ru.leksiinesm.core.rx.disposables.RxDisposable

/**
 * Base view model, that cancels disposables when the view model is going to be destroyed.
 *
 * @param rxDisposable disposable container
 *
 * @author Alexey Nesmelov
 */
open class DisposableViewModel(private val rxDisposable: RxDisposable) : BaseViewModel() {

    override fun onCleared() = rxDisposable.dispose()
}