package ru.leksiinesm.storage.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTIES_DATABASE
import ru.leksiinesm.storage.data.storage.room.database.PlayerDatabase

class PlayerContentProvider : ContentProvider() {

    private lateinit var storage: PlayerDatabase

    override fun onCreate(): Boolean {
        context?.let {
            storage = Room.databaseBuilder(context!!, PlayerDatabase::class.java, PLAYER_PROPERTIES_DATABASE).build()
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}