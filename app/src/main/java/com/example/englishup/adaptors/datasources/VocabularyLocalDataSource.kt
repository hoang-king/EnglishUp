package com.example.englishup.adaptors.datasources

import javax.inject.Inject

interface VocabularyLocalDataSource {
    suspend fun getVocabularyList(): List<VocabularyDto>
    suspend fun saveVocabularyList(list: List<VocabularyDto>)
}

class VocabularyLocalDataSourceImpl @Inject constructor() : VocabularyLocalDataSource {
    private var cache = mutableListOf<VocabularyDto>()

    override suspend fun getVocabularyList(): List<VocabularyDto> {
        return cache
    }

    override suspend fun saveVocabularyList(list: List<VocabularyDto>) {
        cache.clear()
        cache.addAll(list)
    }
}
