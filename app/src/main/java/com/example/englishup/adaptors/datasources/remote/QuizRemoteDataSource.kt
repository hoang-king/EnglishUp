package com.example.englishup.adaptors.datasources.remote


import com.example.englishup.adaptors.datasources.local.Dto.QuizDto

import javax.inject.Inject

interface QuizRemoteDataSource {
    suspend fun fetchQuizList(): List<QuizDto>
}

class QuizRemoteDataSourceImpl @Inject constructor(
    private val apiService: QuizApiService
) : QuizRemoteDataSource {
    override suspend fun fetchQuizList(): List<QuizDto> {
        return try {
            val response = apiService.getQuizzes()
            response.results.mapIndexed { index, result ->
                val allAnswers = (result.incorrect_answers + result.correct_answer)
                    .map { decodeHtml(it) }
                    .shuffled()
                
                QuizDto(
                    id = (index + 1).toString(),
                    question = decodeHtml(result.question),
                    options = allAnswers,
                    correctAnswer = decodeHtml(result.correct_answer)
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun decodeHtml(text: String): String {
        return text.replace("&quot;", "\"")
            .replace("&#039;", "'")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
    }
}


