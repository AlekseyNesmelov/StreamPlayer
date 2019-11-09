package ru.leksiinesm.core.rx.schedullers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxSchedulersImpl : RxSchedulers {

    override val mainThread: Scheduler = AndroidSchedulers.mainThread()

    override val io: Scheduler = Schedulers.io()

    override val computation: Scheduler = Schedulers.computation()
}