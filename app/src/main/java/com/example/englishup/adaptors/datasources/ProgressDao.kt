package com.example.englishup.adaptors.datasources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProgressDao {
    @Query("SELECT * FROM progress")
    suspend fun getAll(): List<ProgressDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ProgressDto>)
}
