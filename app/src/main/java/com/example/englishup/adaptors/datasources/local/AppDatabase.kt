package com.example.englishup.adaptors.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.englishup.adaptors.datasources.local.Dao.GrammarDao
import com.example.englishup.adaptors.datasources.local.Dao.ListeningDao
import com.example.englishup.adaptors.datasources.local.Dao.ProgressDao
import com.example.englishup.adaptors.datasources.local.Dao.QuizDao
import com.example.englishup.adaptors.datasources.local.Dao.ReadingDao
import com.example.englishup.adaptors.datasources.local.Dao.VocabularyDao
import com.example.englishup.adaptors.datasources.local.Dto.GrammarDto
import com.example.englishup.adaptors.datasources.local.Dto.ListeningDto
import com.example.englishup.adaptors.datasources.local.Dto.ProgressDto
import com.example.englishup.adaptors.datasources.local.Dto.QuizDto
import com.example.englishup.adaptors.datasources.local.Dto.ReadingDto
import com.example.englishup.adaptors.datasources.local.Dto.VocabularyDto

@Database(
    entities = [
        GrammarDto::class,
        ListeningDto::class,
        ProgressDto::class,
        QuizDto::class,
        ReadingDto::class,
        VocabularyDto::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun grammarDao(): GrammarDao
    abstract fun listeningDao(): ListeningDao
    abstract fun progressDao(): ProgressDao
    abstract fun quizDao(): QuizDao
    abstract fun readingDao(): ReadingDao
    abstract fun vocabularyDao(): VocabularyDao
}
