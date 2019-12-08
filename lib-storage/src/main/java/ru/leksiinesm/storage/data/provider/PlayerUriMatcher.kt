package ru.leksiinesm.storage.data.provider

import android.content.UriMatcher
import ru.leksiinesm.storage.data.storage.room.contract.AUTHORITY
import ru.leksiinesm.storage.data.storage.room.contract.PLAYER_PROPERTIES_TABLE
import ru.leksiinesm.storage.data.storage.room.contract.URI_PROPERTIES
import ru.leksiinesm.storage.data.storage.room.contract.URI_PROPERTY

object PlayerUriMatcher : UriMatcher(NO_MATCH) {
    init {
        addURI(AUTHORITY, PLAYER_PROPERTIES_TABLE, URI_PROPERTIES)
        addURI(AUTHORITY, "$PLAYER_PROPERTIES_TABLE/*", URI_PROPERTY)
    }
}