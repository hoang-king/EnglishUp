package com.example.englishup.adaptors.datasources.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationApiService {
    @GET("get")
    suspend fun translate(
        @Query("q") text: String,
        @Query("langpair") langPair: String = "en|vi"
    ): TranslationResponse
}

data class TranslationResponse(
    val responseData: TranslationData
)

data class TranslationData(
    val translatedText: String
)
