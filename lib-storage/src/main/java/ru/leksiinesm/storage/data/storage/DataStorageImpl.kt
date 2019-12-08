package ru.leksiinesm.storage.data.storage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.core.database.getStringOrNull
import ru.leksiinesm.core.logger.Logger
import ru.leksiinesm.storage.data.storage.room.contract.IS_LOADING_PROPERTY
import ru.leksiinesm.storage.data.storage.room.contract.IS_PLAYING_PROPERTY
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_DATA_PATH
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTY_NAME
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTY_VALUE

class DataStorageImpl(private val context: Context) : MutableDataStorage {

    override var isPlaying: Boolean
        set(value) = putProperty(IS_PLAYING_PROPERTY, value)
        get() = getProperty(IS_PLAYING_PROPERTY)

    override var isLoading: Boolean
        set(value) = putProperty(IS_LOADING_PROPERTY, value)
        get() = getProperty(IS_LOADING_PROPERTY)

    private fun putProperty(property: String, value: Boolean) {
        Logger.d(TAG, "set $property = $value")
        val values = ContentValues().apply {
            put(PLAYER_PROPERTY_NAME, property)
            put(PLAYER_PROPERTY_VALUE, value)
        }
        context.contentResolver.insert(Uri.parse(PLAYER_DATA_PATH), values)
    }

    private fun getProperty(property: String): Boolean {
        var value = false
        var cursor: Cursor? = null
        try {
            cursor = context
                .contentResolver
                .query(Uri.parse("$PLAYER_DATA_PATH/$property"), null, null, null, null)
            if (cursor?.moveToFirst() == true) {
                value = cursor.getStringOrNull(1) == "1"
            }
        } finally {
            cursor?.close()
        }
        Logger.d(TAG, "get $property = $value")
        return value
    }

    companion object {
        const val TAG = "DataStorageImpl"
    }
}