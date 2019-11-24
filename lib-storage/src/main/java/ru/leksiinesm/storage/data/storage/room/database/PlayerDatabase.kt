package ru.leksiinesm.storage.data.storage.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.leksiinesm.storage.data.storage.room.dao.PlayerDao
import ru.leksiinesm.storage.data.storage.room.entity.PlayerProperty

@Database(entities = [PlayerProperty::class], version = 1)
abstract class PlayerDatabase : RoomDatabase() {
    abstract fun todoDao(): PlayerDao
}