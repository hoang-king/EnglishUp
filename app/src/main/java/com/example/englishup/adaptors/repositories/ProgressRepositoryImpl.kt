package com.example.englishup.adaptors.repositories

import com.example.englishup.adaptors.datasources.local.ProgressLocalDataSource
import com.example.englishup.adaptors.datasources.remote.ProgressRemoteDataSource
import com.example.englishup.adaptors.mapper.ProgressMapper
import com.example.englishup.entities.Progress
import com.example.englishup.repositories.ProgressRepository
import javax.inject.Inject

class ProgressRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProgressRemoteDataSource,
    private val localDataSource: ProgressLocalDataSource
) : ProgressRepository {

    override suspend fun getAll(): List<Progress> {
        return try {
            val remoteData = remoteDataSource.fetchProgressList()
            localDataSource.saveProgressList(remoteData)
            remoteData.map { ProgressMapper(it).toEntity() }
        } catch (e: Exception) {
            localDataSource.getProgressList().map { ProgressMapper(it).toEntity() }
        }
    }

    override suspend fun getById(id: String): Progress? = null
    override suspend fun insert(item: Progress): Long = 0
    override suspend fun update(item: Progress) {}
    override suspend fun delete(item: Progress) {}
}

