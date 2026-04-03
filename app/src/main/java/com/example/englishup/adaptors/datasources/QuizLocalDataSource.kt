package com.example.englishup.adaptors.datasources

import javax.inject.Inject

interface QuizLocalDataSource {
    suspend fun getQuizList(): List<QuizDto>
    suspend fun saveQuizList(list: List<QuizDto>)
}

class QuizLocalDataSourceImpl @Inject constructor() : QuizLocalDataSource {
    private var cache = mutableListOf<QuizDto>()

    override suspend fun getQuizList(): List<QuizDto> {
        return cache
    }

    override suspend fun saveQuizList(list: List<QuizDto>) {
        cache.clear()
        cache.addAll(list)
    }
}
