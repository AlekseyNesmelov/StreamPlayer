package ru.leksiinesm.playerlib.di

import dagger.Component
import ru.leksiinesm.playerlib.service.MediaPlayerService
import javax.inject.Singleton

@Singleton
@Component(modules = [PlayerModule::class])
interface PlayerComponent {
    fun inject(service: MediaPlayerService)
}