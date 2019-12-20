package ru.leksiinesm.playerlib.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.leksiinesm.notification.NotificationBuilder
import ru.leksiinesm.notification.NotificationBuilderImpl
import ru.leksiinesm.playerlib.player.StreamPlayer
import ru.leksiinesm.playerlib.player.StreamPlayerImpl
import javax.inject.Singleton

@Module
class PlayerModule(@get:Provides val context: Context) {

    @Provides
    @Singleton
    fun provideNotificationBuilder(): NotificationBuilder = NotificationBuilderImpl()

    @Provides
    @Singleton
    fun provideStreamPlayer(): StreamPlayer = StreamPlayerImpl(context)
}