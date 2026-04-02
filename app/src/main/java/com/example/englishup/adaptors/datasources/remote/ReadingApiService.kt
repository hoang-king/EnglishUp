package com.example.englishup.adaptors.datasources.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ReadingApiService {
    @GET("quotes/random")
    suspend fun getRandomQuote(
        @Query("limit") limit: Int = 10
    ): List<QuoteResponse>
}

data class QuoteResponse(
    val _id: String,
    val content: String,
    val author: String,
    val tags: List<String>,
    val authorSlug: String,
    val length: Int,
    val dateAdded: String,
    val dateModified: String
)
