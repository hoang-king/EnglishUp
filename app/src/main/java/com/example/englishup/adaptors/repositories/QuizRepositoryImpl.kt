package com.example.englishup.adaptors.repositories

import com.example.englishup.adaptors.datasources.local.QuizLocalDataSource
import com.example.englishup.adaptors.datasources.remote.QuizRemoteDataSource
import com.example.englishup.adaptors.mapper.QuizMapper
import com.example.englishup.entities.Quiz
import com.example.englishup.repositories.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val remoteDataSource: QuizRemoteDataSource,
    private val localDataSource: QuizLocalDataSource
) : QuizRepository {

    override suspend fun getAll(): List<Quiz> {
        return try {
            val remoteData = remoteDataSource.fetchQuizList()
            localDataSource.saveQuizList(remoteData)
            remoteData.map { QuizMapper(it).toEntity() }
        } catch (e: Exception) {
            localDataSource.getQuizList().map { QuizMapper(it).toEntity() }
        }
    }

    override suspend fun getById(id: String): Quiz? = null
    override suspend fun insert(item: Quiz): Long = 0
    override suspend fun update(item: Quiz) {}
    override suspend fun delete(item: Quiz) {}
}

