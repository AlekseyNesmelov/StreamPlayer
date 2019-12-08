package ru.leksiinesm.storage.data.storage.room.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTIES_TABLE
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTY_NAME
import ru.leksiinesm.storage.data.storage.room.entity.PlayerProperty

@Dao
interface PlayerDao {

    @Query("SELECT * FROM $PLAYER_PROPERTIES_TABLE")
    fun getAll() : Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putProperty(property: PlayerProperty)

    @Query("SELECT * FROM $PLAYER_PROPERTIES_TABLE WHERE $PLAYER_PROPERTY_NAME = :key LIMIT 1")
    fun getProperty(key: String): Cursor
}