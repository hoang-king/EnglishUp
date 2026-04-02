package com.example.englishup.adaptors.datasources.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.englishup.adaptors.datasources.local.Dto.GrammarDto

@Dao
interface GrammarDao {
    @Query("SELECT * FROM grammar")
    suspend fun getAll(): List<GrammarDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(grammarList: List<GrammarDto>)

    @Query("SELECT * FROM grammar WHERE id = :id")
    suspend fun getById(id: String): GrammarDto?
}