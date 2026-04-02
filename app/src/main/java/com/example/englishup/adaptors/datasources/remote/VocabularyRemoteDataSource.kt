package com.example.englishup.adaptors.datasources.remote

import com.example.englishup.adaptors.datasources.local.Dto.VocabularyDto
import com.example.englishup.entities.WordStatus
import javax.inject.Inject

interface VocabularyRemoteDataSource {
    suspend fun fetchVocabularyDetails(words: List<String>): List<VocabularyDto>
    suspend fun fetchVocabularyList(): List<VocabularyDto> // Keep for compatibility
}

class VocabularyRemoteDataSourceImpl @Inject constructor(
    private val apiService: VocabularyApiService,
    private val translationApiService: TranslationApiService
) : VocabularyRemoteDataSource {

    override suspend fun fetchVocabularyDetails(words: List<String>): List<VocabularyDto> {
        val result = mutableListOf<VocabularyDto>()
        
        for (word in words) {
            try {
                val responseList = apiService.getWordDetail(word.lowercase().trim())
                if (responseList.isNotEmpty()) {
                    val firstResult = responseList[0]
                    val phonetic = firstResult.phonetic ?: firstResult.phonetics?.firstOrNull { it.text != null }?.text ?: ""
                    val audioUrl = firstResult.phonetics?.firstOrNull { !it.audio.isNullOrEmpty() }?.audio ?: ""
                    val meaning = firstResult.meanings?.firstOrNull()
                    val definitionEn = meaning?.definitions?.firstOrNull()?.definition ?: ""
                    val example = meaning?.definitions?.firstOrNull()?.example ?: ""
                    
                    // Dịch nghĩa sang tiếng Việt
                    val definitionVi = try {
                        val translationResponse = translationApiService.translate(definitionEn)
                        translationResponse.responseData.translatedText
                    } catch (e: Exception) {
                        "Chưa có bản dịch"
                    }

                    result.add(
                        VocabularyDto(
                            id = word.lowercase(),
                            word = firstResult.word.replaceFirstChar { it.uppercase() },
                            phonetic = phonetic,
                            partOfSpeech = meaning?.partOfSpeech ?: "noun",
                            definitionEn = definitionEn,
                            definitionVi = definitionVi,
                            example = example,
                            audioUrl = audioUrl,
                            category = "Daily Word",
                            status = WordStatus.NEW
                        )
                    )
                }
            } catch (e: Exception) {
                // Bỏ qua nếu không tìm thấy từ trong từ điển
            }
        }
        return result
    }

    override suspend fun fetchVocabularyList(): List<VocabularyDto> {
        return fetchVocabularyDetails(listOf("hello", "world", "learning", "happiness", "success"))
    }
}
