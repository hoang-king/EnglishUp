package com.example.englishup.repositories

import com.example.englishup.core.BaseRepository
import com.example.englishup.entities.Vocabulary
import kotlinx.coroutines.flow.Flow

interface VocabularyRepository : BaseRepository<Vocabulary> {
    fun getAllFlow(): Flow<List<Vocabulary>>
    fun getDueForReview(today: String): Flow<List<Vocabulary>>
    suspend fun search(query: String): List<Vocabulary>
    suspend fun updateReviewSchedule(vocabulary: Vocabulary)
}
