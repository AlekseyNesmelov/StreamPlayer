package ru.leksiinesm.core.viewmodel

import io.reactivex.disposables.CompositeDisposable
import ru.leksiinesm.core.rx.disposables.RxDisposable
import ru.leksiinesm.core.rx.disposables.RxDisposableImpl

/**
 * Base view model, that cancels disposables when the view model is going to be destroyed.
 *
 * @param rxDisposable disposable container
 *
 * @author Alexey Nesmelov
 */
open class DisposableViewModel : BaseViewModel() {

    protected val rxDisposable: RxDisposable = RxDisposableImpl(CompositeDisposable())

    override fun onCleared() = rxDisposable.dispose()
}