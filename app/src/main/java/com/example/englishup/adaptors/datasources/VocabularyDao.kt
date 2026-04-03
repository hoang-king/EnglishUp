package com.example.englishup.adaptors.datasources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VocabularyDao {
    @Query("SELECT * FROM vocabulary")
    suspend fun getAll(): List<VocabularyDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<VocabularyDto>)
}
