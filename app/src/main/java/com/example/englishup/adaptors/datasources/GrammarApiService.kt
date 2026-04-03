package com.example.englishup.adaptors.datasources

import retrofit2.http.GET

interface GrammarApiService {
    @GET("grammar") // Placeholder endpoint
    suspend fun getGrammarList(): List<GrammarDto>
}
