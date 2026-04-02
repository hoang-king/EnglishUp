package com.example.englishup.adaptors.datasources.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.englishup.adaptors.datasources.local.Dto.VocabularyDto
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyDao {
    @Query("SELECT * FROM vocabulary")
    fun getAllFlow(): Flow<List<VocabularyDto>>

    @Query("SELECT * FROM vocabulary")
    suspend fun getAll(): List<VocabularyDto>

    @Query("SELECT * FROM vocabulary WHERE category = :category")
    fun getByCategory(category: String): Flow<List<VocabularyDto>>

    @Query("SELECT * FROM vocabulary WHERE nextReviewDate <= :today")
    fun getDueForReview(today: String): Flow<List<VocabularyDto>>

    @Query("SELECT * FROM vocabulary WHERE word LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<VocabularyDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<VocabularyDto>)

    @Update
    suspend fun update(dto: VocabularyDto)
}
