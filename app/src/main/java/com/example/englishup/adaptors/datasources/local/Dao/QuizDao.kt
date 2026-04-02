package com.example.englishup.adaptors.datasources.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.englishup.adaptors.datasources.local.Dto.QuizDto

@Dao
interface QuizDao {
    @Query("SELECT * FROM quiz")
    suspend fun getAll(): List<QuizDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<QuizDto>)
}