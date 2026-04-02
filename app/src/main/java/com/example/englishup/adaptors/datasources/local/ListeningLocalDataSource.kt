package com.example.englishup.adaptors.datasources.local

import com.example.englishup.adaptors.datasources.local.Dto.ListeningDto
import javax.inject.Inject

interface ListeningLocalDataSource {
    suspend fun getListeningList(): List<ListeningDto>
    suspend fun saveListeningList(list: List<ListeningDto>)
}

class ListeningLocalDataSourceImpl @Inject constructor() : ListeningLocalDataSource {
    private var cache = mutableListOf<ListeningDto>()

    override suspend fun getListeningList(): List<ListeningDto> {
        return cache
    }

    override suspend fun saveListeningList(list: List<ListeningDto>) {
        cache.clear()
        cache.addAll(list)
    }
}
