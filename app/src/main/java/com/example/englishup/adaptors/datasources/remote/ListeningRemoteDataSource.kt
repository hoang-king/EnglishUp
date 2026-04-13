package com.example.englishup.adaptors.datasources.remote

import com.example.englishup.adaptors.datasources.local.Dto.ListeningDto
import javax.inject.Inject

interface ListeningRemoteDataSource {
    suspend fun fetchListeningList(): List<ListeningDto>
}

class ListeningRemoteDataSourceImpl @Inject constructor(
    private val apiService: ListeningApiService
) : ListeningRemoteDataSource {
    override suspend fun fetchListeningList(): List<ListeningDto> {
        val words = listOf("hello", "learning", "language", "english", "education", "science", "travel", "culture", "nature", "success")
        return try {
            words.mapIndexed { index, word ->
                try {
                    val response = apiService.getWordInfo(word)
                    val audioUrl = response.firstOrNull()?.phonetics?.firstOrNull { it.audio?.isNotEmpty() == true }?.audio
                    
                    ListeningDto(
                        id = (index + 1).toString(),
                        title = "Word: ${word.replaceFirstChar { it.uppercase() }}",
                        description = "Listen to the pronunciation and practice your listening skills.",
                        audioUrl = audioUrl ?: "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
                    )
                } catch (e: Exception) {
                    // Fallback for individual word if API fails
                    ListeningDto(
                        id = (index + 1).toString(),
                        title = "Word: ${word.replaceFirstChar { it.uppercase() }} (Offline)",
                        description = "Listen and practice (Sample audio).",
                        audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-${(index % 4) + 1}.mp3"
                    )
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
