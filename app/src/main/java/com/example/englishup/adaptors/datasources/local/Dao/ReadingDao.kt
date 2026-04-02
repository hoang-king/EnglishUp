package com.example.englishup.adaptors.datasources.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.englishup.adaptors.datasources.local.Dto.ReadingDto

@Dao
interface ReadingDao {
    @Query("SELECT * FROM reading")
    suspend fun getAll(): List<ReadingDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ReadingDto>)
}