package com.example.englishup.adaptors.datasources

import javax.inject.Inject

interface QuizRemoteDataSource {
    suspend fun fetchQuizList(): List<QuizDto>
}

class QuizRemoteDataSourceImpl @Inject constructor() : QuizRemoteDataSource {
    override suspend fun fetchQuizList(): List<QuizDto> {
        return listOf(
            QuizDto("1", "What is the capital of France?", listOf("London", "Berlin", "Paris", "Madrid"), "Paris"),
            QuizDto("2", "Which planet is known as the Red Planet?", listOf("Earth", "Mars", "Jupiter", "Venus"), "Mars"),
            QuizDto("3", "What is the largest mammal?", listOf("Elephant", "Blue Whale", "Giraffe", "Shark"), "Blue Whale")
        )
    }
}
