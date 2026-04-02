package com.example.englishup.di

import com.example.englishup.adaptors.datasources.local.*
import com.example.englishup.adaptors.datasources.remote.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindGrammarRemoteDataSource(
        grammarRemoteDataSourceImpl: GrammarRemoteDataSourceImpl
    ): GrammarRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindGrammarLocalDataSource(
        grammarLocalDataSourceImpl: GrammarLocalDataSourceImpl
    ): GrammarLocalDataSource

    @Binds
    @Singleton
    abstract fun bindListeningRemoteDataSource(
        listeningRemoteDataSourceImpl: ListeningRemoteDataSourceImpl
    ): ListeningRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindListeningLocalDataSource(
        listeningLocalDataSourceImpl: ListeningLocalDataSourceImpl
    ): ListeningLocalDataSource

    @Binds
    @Singleton
    abstract fun bindProgressRemoteDataSource(
        progressRemoteDataSourceImpl: ProgressRemoteDataSourceImpl
    ): ProgressRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindProgressLocalDataSource(
        progressLocalDataSourceImpl: ProgressLocalDataSourceImpl
    ): ProgressLocalDataSource

    @Binds
    @Singleton
    abstract fun bindQuizRemoteDataSource(
        quizRemoteDataSourceImpl: QuizRemoteDataSourceImpl
    ): QuizRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindQuizLocalDataSource(
        quizLocalDataSourceImpl: QuizLocalDataSourceImpl
    ): QuizLocalDataSource

    @Binds
    @Singleton
    abstract fun bindReadingRemoteDataSource(
        readingRemoteDataSourceImpl: ReadingRemoteDataSourceImpl
    ): ReadingRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindReadingLocalDataSource(
        readingLocalDataSourceImpl: ReadingLocalDataSourceImpl
    ): ReadingLocalDataSource

    @Binds
    @Singleton
    abstract fun bindVocabularyRemoteDataSource(
        vocabularyRemoteDataSourceImpl: VocabularyRemoteDataSourceImpl
    ): VocabularyRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindVocabularyLocalDataSource(
        vocabularyLocalDataSourceImpl: VocabularyLocalDataSourceImpl
    ): VocabularyLocalDataSource
}

