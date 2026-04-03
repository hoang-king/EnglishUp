package com.example.englishup.adaptors.datasources

import javax.inject.Inject

interface VocabularyRemoteDataSource {
    suspend fun fetchVocabularyList(): List<VocabularyDto>
}

class VocabularyRemoteDataSourceImpl @Inject constructor() : VocabularyRemoteDataSource {
    override suspend fun fetchVocabularyList(): List<VocabularyDto> {
        return listOf(
            VocabularyDto("1", "Serendipity", "Sự tình cờ may mắn", "/ˌserənˈdɪpəti/", "Meeting her was pure serendipity."),
            VocabularyDto("2", "Ephemeral", "Chóng vánh, phù du", "/ɪˈfemərəl/", "The beauty of sunset is ephemeral."),
            VocabularyDto("3", "Eloquent", "Hùng hồn, trôi chảy", "/ˈeləkwənt/", "She made an eloquent speech.")
        )
    }
}
