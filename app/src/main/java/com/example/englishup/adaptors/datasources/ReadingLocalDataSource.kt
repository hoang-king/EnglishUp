package com.example.englishup.adaptors.datasources.local

import com.example.englishup.adaptors.datasources.local.Dao.ReadingDao
import com.example.englishup.adaptors.datasources.local.Dto.ReadingDto
import javax.inject.Inject

interface ReadingLocalDataSource {
    suspend fun getReadingList(): List<ReadingDto>
    suspend fun saveReadingList(list: List<ReadingDto>)
}

class ReadingLocalDataSourceImpl @Inject constructor(
    private val readingDao: ReadingDao
) : ReadingLocalDataSource {

    override suspend fun getReadingList(): List<ReadingDto> {
        return readingDao.getAll()
    }

    override suspend fun saveReadingList(list: List<ReadingDto>) {
        readingDao.insertAll(list)
    }
}
