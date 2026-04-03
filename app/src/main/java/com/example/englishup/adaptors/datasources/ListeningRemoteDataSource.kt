package com.example.englishup.adaptors.datasources

import javax.inject.Inject

interface ListeningRemoteDataSource {
    suspend fun fetchListeningList(): List<ListeningDto>
}

class ListeningRemoteDataSourceImpl @Inject constructor() : ListeningRemoteDataSource {
    override suspend fun fetchListeningList(): List<ListeningDto> {
        return listOf(
            ListeningDto("1", "Daily Conversation", "Listen to a simple conversation.", "https://example.com/audio1.mp3"),
            ListeningDto("2", "Business Meeting", "Understand key points in a meeting.", "https://example.com/audio2.mp3"),
            ListeningDto("3", "Travel Tips", "Listen to travel recommendations.", "https://example.com/audio3.mp3")
        )
    }
}
