package com.example.englishup.adaptors.datasources

interface GrammarLocalDataSource {
    suspend fun getGrammarList(): List<GrammarDto>
    suspend fun saveGrammarList(list: List<GrammarDto>)
}

class GrammarLocalDataSourceImpl : GrammarLocalDataSource {
    private var cache = mutableListOf<GrammarDto>()

    override suspend fun getGrammarList(): List<GrammarDto> {
        return cache
    }

    override suspend fun saveGrammarList(list: List<GrammarDto>) {
        cache.clear()
        cache.addAll(list)
    }
}
