package com.example.englishup.adaptors.datasources

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        GrammarDto::class,
        ListeningDto::class,
        ProgressDto::class,
        QuizDto::class,
        ReadingDto::class,
        VocabularyDto::class
    ],
    version = 1
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
