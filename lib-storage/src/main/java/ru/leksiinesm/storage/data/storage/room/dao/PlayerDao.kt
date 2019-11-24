package ru.leksiinesm.storage.data.storage.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTIES_TABLE
import ru.leksiinesm.storage.data.storage.room.entity.PlayerProperty

@Dao
interface PlayerDao {

    @Query("SELECT * FROM $PLAYER_PROPERTIES_TABLE")
    fun getAll() : List<PlayerProperty>
}