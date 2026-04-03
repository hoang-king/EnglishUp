package com.example.englishup.adaptors.datasources

import javax.inject.Inject

interface ProgressRemoteDataSource {
    suspend fun fetchProgressList(): List<ProgressDto>
}

class ProgressRemoteDataSourceImpl @Inject constructor() : ProgressRemoteDataSource {
    override suspend fun fetchProgressList(): List<ProgressDto> {
        return listOf(
            ProgressDto("1", "user_101", 85, 12),
            ProgressDto("2", "user_102", 92, 15),
            ProgressDto("3", "user_103", 78, 10)
        )
    }
}
