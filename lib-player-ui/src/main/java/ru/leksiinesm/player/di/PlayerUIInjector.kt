package ru.leksiinesm.player.di

import ru.leksiinesm.storage.data.storage.DataStorage
import javax.inject.Singleton

@Singleton
interface PlayerUIInjector {

    fun getDataStorage(): DataStorage
}