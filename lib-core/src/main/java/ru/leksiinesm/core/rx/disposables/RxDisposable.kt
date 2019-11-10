package ru.leksiinesm.core.rx.disposables

import io.reactivex.disposables.Disposable

/**
 * Default RX disposable container.
 *
 * @author Alexey Nesmelov
 */
interface RxDisposable {

    /**
     * Add disposable to container.
     */
    fun add(disposable: Disposable)

    /**
     * Cancel all disposables.
     */
    fun dispose()
}