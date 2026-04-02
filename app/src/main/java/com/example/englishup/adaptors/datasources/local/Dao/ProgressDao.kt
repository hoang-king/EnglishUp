package com.example.englishup.adaptors.datasources.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.englishup.adaptors.datasources.local.Dto.ProgressDto

@Dao
interface ProgressDao {
    @Query("SELECT * FROM progress")
    suspend fun getAll(): List<ProgressDto>

    @Query("SELECT * FROM progress WHERE date = :date")
    suspend fun getByDate(date: String): ProgressDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ProgressDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: ProgressDto)
}