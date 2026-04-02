package com.example.englishup.di

import android.content.Context
import androidx.room.Room
import com.example.englishup.adaptors.datasources.local.AppDatabase
import com.example.englishup.adaptors.datasources.local.Dao.GrammarDao
import com.example.englishup.adaptors.datasources.local.Dao.ListeningDao
import com.example.englishup.adaptors.datasources.local.Dao.ProgressDao
import com.example.englishup.adaptors.datasources.local.Dao.QuizDao
import com.example.englishup.adaptors.datasources.local.Dao.ReadingDao
import com.example.englishup.adaptors.datasources.local.Dao.VocabularyDao
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
        ).build()
    }

    @Provides
    fun provideGrammarDao(database: AppDatabase): GrammarDao {
        return database.grammarDao()
    }

    @Provides
    fun provideListeningDao(database: AppDatabase): ListeningDao {
        return database.listeningDao()
    }

    @Provides
    fun provideProgressDao(database: AppDatabase): ProgressDao {
        return database.progressDao()
    }

    @Provides
    fun provideQuizDao(database: AppDatabase): QuizDao {
        return database.quizDao()
    }

    @Provides
    fun provideReadingDao(database: AppDatabase): ReadingDao {
        return database.readingDao()
    }

    @Provides
    fun provideVocabularyDao(database: AppDatabase): VocabularyDao {
        return database.vocabularyDao()
    }
}

