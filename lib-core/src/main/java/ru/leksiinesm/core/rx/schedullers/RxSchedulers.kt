package ru.leksiinesm.core.rx.schedullers

import io.reactivex.Scheduler

/**
 * Rx schedulers class, that returns standard scheduler instances.
 *
 * @author Alexey Nesmelov
 */
interface RxSchedulers {

    /**
     * Main thread scheduler.
     */
    val mainThread: Scheduler

    /**
     * Simple thread scheduler.
     */
    val io: Scheduler

    /**
     * Scheduler for computation.
     */
    val computation: Scheduler
}