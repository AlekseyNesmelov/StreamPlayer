package ru.leksiinesm.core.rx.schedullers

import io.reactivex.Scheduler

/**
 * Rx schedulers class, that returns standard scheduler instances.
 *
 * @author Alexey Nesmelov
 */
interface RxSchedulers {

    val mainThread: Scheduler

    val io: Scheduler

    val computation: Scheduler
}