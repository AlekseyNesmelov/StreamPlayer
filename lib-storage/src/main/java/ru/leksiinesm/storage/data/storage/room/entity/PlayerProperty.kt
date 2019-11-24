package ru.leksiinesm.storage.data.storage.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTIES_TABLE
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTY_NAME
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTY_VALUE

@Entity(tableName = PLAYER_PROPERTIES_TABLE)
class PlayerProperty(
    @PrimaryKey
    @ColumnInfo(name = PLAYER_PROPERTY_NAME)
    val propertyName: String,

    @ColumnInfo(name = PLAYER_PROPERTY_VALUE)
    val propertyValue: String
)
