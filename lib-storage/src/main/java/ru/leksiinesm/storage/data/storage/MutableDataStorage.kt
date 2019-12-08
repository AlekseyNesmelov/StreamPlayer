package ru.leksiinesm.storage.data.storage

interface MutableDataStorage : DataStorage {

    override var isPlaying : Boolean

    override var isLoading : Boolean
}