package com.example.englishup.adaptors.datasources

import javax.inject.Inject

interface ReadingRemoteDataSource {
    suspend fun fetchReadingList(): List<ReadingDto>
}

class ReadingRemoteDataSourceImpl @Inject constructor() : ReadingRemoteDataSource {
    override suspend fun fetchReadingList(): List<ReadingDto> {
        return listOf(
            ReadingDto("1", "The Great Gatsby", "In my younger and more vulnerable years...", "Advanced"),
            ReadingDto("2", "Alice in Wonderland", "Alice was beginning to get very tired...", "Intermediate"),
            ReadingDto("3", "The Little Prince", "Once when I was six years old...", "Beginner")
        )
    }
}
