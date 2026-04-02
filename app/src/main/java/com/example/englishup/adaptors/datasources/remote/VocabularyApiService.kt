package com.example.englishup.adaptors.datasources.remote

import com.example.englishup.adaptors.datasources.local.Dto.VocabularyDto
import retrofit2.http.GET
import retrofit2.http.Path

interface VocabularyApiService {
    @GET("entries/en/{word}")
    suspend fun getWordDetail(@Path("word") word: String): List<DictionaryResponse>
}

data class DictionaryResponse(
    val word: String,
    val phonetic: String?,
    val phonetics: List<Phonetic>?,
    val meanings: List<Meaning>?
)

data class Phonetic(
    val text: String?,
    val audio: String?
)

data class Meaning(
    val partOfSpeech: String?,
    val definitions: List<Definition>?
)

data class Definition(
    val definition: String?,
    val example: String?
)
