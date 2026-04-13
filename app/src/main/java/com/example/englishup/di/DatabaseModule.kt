package com.example.englishup.di

import android.content.Context
import androidx.room.Room
import com.example.englishup.adaptors.datasources.local.AppDatabase
import com.example.englishup.adaptors.datasources.local.Dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "english_up_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideGrammarDao(database: AppDatabase): GrammarDao = database.grammarDao()

    @Provides
    fun provideListeningDao(database: AppDatabase): ListeningDao = database.listeningDao()

    @Provides
    fun provideProgressDao(database: AppDatabase): ProgressDao = database.progressDao()

    @Provides
    fun provideQuizDao(database: AppDatabase): QuizDao = database.quizDao()

    @Provides
    fun provideReadingDao(database: AppDatabase): ReadingDao = database.readingDao()

    @Provides
    fun provideVocabularyDao(database: AppDatabase): VocabularyDao = database.vocabularyDao()

    @Provides
    fun provideReviewLogDao(database: AppDatabase): ReviewLogDao = database.reviewLogDao()
}

