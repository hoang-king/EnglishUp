package com.example.englishup.adaptors.datasources

import javax.inject.Inject

interface ReadingLocalDataSource {
    suspend fun getReadingList(): List<ReadingDto>
    suspend fun saveReadingList(list: List<ReadingDto>)
}

class ReadingLocalDataSourceImpl @Inject constructor() : ReadingLocalDataSource {
    private var cache = mutableListOf<ReadingDto>()

    override suspend fun getReadingList(): List<ReadingDto> {
        return cache
    }

    override suspend fun saveReadingList(list: List<ReadingDto>) {
        cache.clear()
        cache.addAll(list)
    }
}
