package com.example.englishup.di

import com.example.englishup.adaptors.repositories.GrammarRepositoryImpl
import com.example.englishup.adaptors.repositories.ListeningRepositoryImpl
import com.example.englishup.adaptors.repositories.ProgressRepositoryImpl
import com.example.englishup.adaptors.repositories.QuizRepositoryImpl
import com.example.englishup.adaptors.repositories.ReadingRepositoryImpl
import com.example.englishup.adaptors.repositories.VocabularyRepositoryImpl
import com.example.englishup.repositories.GrammarRepository
import com.example.englishup.repositories.ListeningRepository
import com.example.englishup.repositories.ProgressRepository
import com.example.englishup.repositories.QuizRepository
import com.example.englishup.repositories.ReadingRepository
import com.example.englishup.repositories.VocabularyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGrammarRepository(
        grammarRepositoryImpl: GrammarRepositoryImpl
    ): GrammarRepository

    @Binds
    @Singleton
    abstract fun bindListeningRepository(
        listeningRepositoryImpl: ListeningRepositoryImpl
    ): ListeningRepository

    @Binds
    @Singleton
    abstract fun bindProgressRepository(
        progressRepositoryImpl: ProgressRepositoryImpl
    ): ProgressRepository

    @Binds
    @Singleton
    abstract fun bindQuizRepository(
        quizRepositoryImpl: QuizRepositoryImpl
    ): QuizRepository

    @Binds
    @Singleton
    abstract fun bindReadingRepository(
        readingRepositoryImpl: ReadingRepositoryImpl
    ): ReadingRepository

    @Binds
    @Singleton
    abstract fun bindVocabularyRepository(
        vocabularyRepositoryImpl: VocabularyRepositoryImpl
    ): VocabularyRepository
}
