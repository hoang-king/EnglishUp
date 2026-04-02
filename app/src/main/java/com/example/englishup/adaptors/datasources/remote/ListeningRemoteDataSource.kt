package com.example.englishup.adaptors.datasources.remote

import com.example.englishup.adaptors.datasources.local.Dto.ListeningDto
import javax.inject.Inject

interface ListeningRemoteDataSource {
    suspend fun fetchListeningList(): List<ListeningDto>
}

class ListeningRemoteDataSourceImpl @Inject constructor() : ListeningRemoteDataSource {
    override suspend fun fetchListeningList(): List<ListeningDto> {
        // Trả về dữ liệu TOEIC Listening ngay lập tức, không block UI bằng network request chậm
        return listOf(
            ListeningDto(
                id = "1",
                title = "Part 1: Photographs",
                description = "Listen and choose the statement that best describes the picture.",
                audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
            ),
            ListeningDto(
                id = "2",
                title = "Part 2: Question-Response",
                description = "Listen to the question and choose the best response.",
                audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"
            ),
            ListeningDto(
                id = "3",
                title = "Part 3: Short Conversations",
                description = "Listen to a short conversation between two or more people.",
                audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
            ),
            ListeningDto(
                id = "4",
                title = "Part 4: Short Talks",
                description = "Listen to a short talk given by a single speaker.",
                audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3"
            )
        )
    }
}
