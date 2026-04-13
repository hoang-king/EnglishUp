package com.example.englishup.adaptors.datasources.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.englishup.adaptors.datasources.local.Dto.ReviewLogDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewLogDao {
    @Query("SELECT * FROM review_logs ORDER BY timestamp DESC")
    fun getAllFlow(): Flow<List<ReviewLogDto>>

    @Query("SELECT * FROM review_logs WHERE timestamp LIKE :date || '%'")
    fun getByDate(date: String): Flow<List<ReviewLogDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: ReviewLogDto)

    @Query("DELETE FROM review_logs")
    suspend fun deleteAll()
}
