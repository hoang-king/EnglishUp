package com.example.englishup.adaptors.datasources.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ListeningApiService {
    @GET("entries/en/{word}")
    suspend fun getWordInfo(
        @Path("word") word: String
    ): List<ListeningDictionaryEntry>
}

data class ListeningDictionaryEntry(
    val word: String,
    val phonetics: List<ListeningPhonetic>
)

data class ListeningPhonetic(
    val text: String?,
    val audio: String?
)
