package com.example.englishup.adaptors.datasources

interface GrammarRemoteDataSource {
    suspend fun fetchGrammarList(): List<GrammarDto>
}

class GrammarRemoteDataSourceImpl : GrammarRemoteDataSource {
    override suspend fun fetchGrammarList(): List<GrammarDto> {
        return listOf(
            GrammarDto("1", "Present Simple", "Thì hiện tại đơn", "[ˈprɛzənt ˈsɪmpəl]"),
            GrammarDto("2", "Past Simple", "Thì quá khứ đơn", "[pæst ˈsɪmpəl]"),
            GrammarDto("3", "Future Simple", "Thì tương lai đơn", "[ˈfjutʃər ˈsɪmpəl]")
        )
    }
}
