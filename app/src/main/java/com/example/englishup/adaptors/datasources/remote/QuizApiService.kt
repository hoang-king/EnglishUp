package com.example.englishup.adaptors.datasources.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApiService {
    @GET("api.php")
    suspend fun getQuizzes(
        @Query("amount") amount: Int = 10,
        @Query("type") type: String = "multiple"
    ): QuizApiResponse
}

data class QuizApiResponse(
    val response_code: Int,
    val results: List<QuizResult>
)

data class QuizResult(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)
