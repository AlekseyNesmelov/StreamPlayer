package ru.leksiinesm.storage.data.storage

import android.content.Context
import android.content.Context.MODE_MULTI_PROCESS
import android.content.SharedPreferences
import ru.leksiinesm.core.logger.Logger

class DataStorageImpl(private val context: Context) : DataStorage {

    private val prefs: SharedPreferences by lazy { context.getSharedPreferences(PREFS_NAME, MODE_MULTI_PROCESS) }

    override var isPlaying: Boolean
        set(value) {
            Logger.d(PREFS_NAME, "set IsPlaying $value")
            prefs.edit()
                .putBoolean(IS_PLAYING_KEY, value)
                .apply()
        }
        get() {
            val value = prefs.getBoolean(IS_PLAYING_KEY, false)
            Logger.d(PREFS_NAME, "get IsPlaying $value")
            return value
        }

    companion object {
        const val PREFS_NAME = "PlayerDataStorage"

        const val IS_PLAYING_KEY = "IsPlayingKey"
    }
}