package ru.leksiinesm.playerlib.player

interface StreamPlayer {

    fun start()

    fun stop()

    fun setSource(url: String)
}