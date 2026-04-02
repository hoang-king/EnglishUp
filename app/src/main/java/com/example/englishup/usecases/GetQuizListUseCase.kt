package com.example.englishup.usecases

import com.example.englishup.entities.Quiz
import com.example.englishup.repositories.QuizRepository
import javax.inject.Inject

class GetQuizListUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend fun execute(): List<Quiz> {
        return repository.getAll()
    }
}
