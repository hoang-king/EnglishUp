package com.example.englishup.adaptors.datasources

import androidx.room.*

@Dao
interface GrammarDao {
    @Query("SELECT * FROM grammar")
    suspend fun getAll(): List<GrammarDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(grammarList: List<GrammarDto>)

    @Query("SELECT * FROM grammar WHERE id = :id")
    suspend fun getById(id: String): GrammarDto?
}
