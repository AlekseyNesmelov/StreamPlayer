package ru.leksiinesm.playerlib.focus

import io.reactivex.Observable

interface AudioFocusManager {

    fun start()

    fun release()

    val focusChangeObservable: Observable<AudioFocusState>
}