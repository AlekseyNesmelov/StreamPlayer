package ru.leksiinesm.storage.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.leksiinesm.storage.data.storage.DataStorage
import ru.leksiinesm.storage.data.storage.DataStorageImpl
import javax.inject.Singleton

@Module
class DataStorageModule {

    @Provides
    @Singleton
    fun provideDataStorage(context: Context): DataStorage = DataStorageImpl(context)
}