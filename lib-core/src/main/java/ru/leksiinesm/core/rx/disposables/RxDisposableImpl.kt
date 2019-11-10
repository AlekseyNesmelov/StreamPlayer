package ru.leksiinesm.core.rx.disposables

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Default implementation of RX disposable container.
 *
 * @param container [CompositeDisposable] to delegate to.
 *
 * @author Alexey Nesmelov
 */
class RxDisposableImpl(private val container: CompositeDisposable) : RxDisposable {

    override fun add(disposable: Disposable) {
        container.add(disposable)
    }

    override fun dispose() = container.dispose()
}