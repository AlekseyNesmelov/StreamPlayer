package ru.leksiinesm.storage.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_DATA_PATH
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTIES_DATABASE
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTY_NAME
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTY_VALUE
import ru.leksiinesm.storage.data.storage.room.contract.URI_PROPERTIES
import ru.leksiinesm.storage.data.storage.room.contract.URI_PROPERTY
import ru.leksiinesm.storage.data.storage.room.dao.PlayerDao
import ru.leksiinesm.storage.data.storage.room.database.PlayerDatabase
import ru.leksiinesm.storage.data.storage.room.entity.PlayerProperty

class PlayerContentProvider : ContentProvider() {

    private lateinit var storage: PlayerDatabase
    private lateinit var dao: PlayerDao

    override fun onCreate(): Boolean {
        context?.let {
            storage = Room.databaseBuilder(it, PlayerDatabase::class.java, PLAYER_PROPERTIES_DATABASE)
                .allowMainThreadQueries().build()
            dao = storage.getDao()
        }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var cursor: Cursor? = null
        when (PlayerUriMatcher.match(uri)) {
            URI_PROPERTIES -> cursor = dao.getAll()
            URI_PROPERTY -> {
                uri.lastPathSegment?.let {
                    cursor = dao.getProperty(it)
                }
            }
            else -> throw UnsupportedOperationException("URI is not supported")
        }
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (PlayerUriMatcher.match(uri) != URI_PROPERTIES) {
            throw UnsupportedOperationException("URI is not supported")
        }
        val name = values?.get(PLAYER_PROPERTY_NAME) as? String
        val value = values?.get(PLAYER_PROPERTY_VALUE) as? Boolean
        if (name == null || value == null) {
            throw UnsupportedOperationException("Property was nat set")
        }
        dao.putProperty(PlayerProperty(name, value))
        val resultUri = Uri.parse("$PLAYER_DATA_PATH/$name")
        context?.contentResolver?.notifyChange(resultUri, null)
        return resultUri
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("URI is not supported")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("Operation is not supported")
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException("Operation is not supported")
    }
}