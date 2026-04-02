package com.example.englishup.adaptors.datasources.local

import com.example.englishup.adaptors.datasources.local.Dto.GrammarDto

import javax.inject.Inject

interface GrammarLocalDataSource {
    suspend fun getGrammarList(): List<GrammarDto>
    suspend fun saveGrammarList(list: List<GrammarDto>)
}

class GrammarLocalDataSourceImpl @Inject constructor() : GrammarLocalDataSource {
    private var cache = mutableListOf<GrammarDto>()

    override suspend fun getGrammarList(): List<GrammarDto> {
        return cache
    }

    override suspend fun saveGrammarList(list: List<GrammarDto>) {
        cache.clear()
        cache.addAll(list)
    }
}
