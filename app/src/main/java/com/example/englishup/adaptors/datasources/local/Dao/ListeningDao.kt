package com.example.englishup.adaptors.datasources.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.englishup.adaptors.datasources.local.Dto.ListeningDto

@Dao
interface ListeningDao {
    @Query("SELECT * FROM listening")
    suspend fun getAll(): List<ListeningDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ListeningDto>)
}