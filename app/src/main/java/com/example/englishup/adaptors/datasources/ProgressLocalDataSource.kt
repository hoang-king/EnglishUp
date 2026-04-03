package com.example.englishup.adaptors.datasources

import javax.inject.Inject

interface ProgressLocalDataSource {
    suspend fun getProgressList(): List<ProgressDto>
    suspend fun saveProgressList(list: List<ProgressDto>)
}

class ProgressLocalDataSourceImpl @Inject constructor() : ProgressLocalDataSource {
    private var cache = mutableListOf<ProgressDto>()

    override suspend fun getProgressList(): List<ProgressDto> {
        return cache
    }

    override suspend fun saveProgressList(list: List<ProgressDto>) {
        cache.clear()
        cache.addAll(list)
    }
}
