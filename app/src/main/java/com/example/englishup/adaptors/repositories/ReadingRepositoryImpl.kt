package com.example.englishup.adaptors.repositories

import android.util.Log
import com.example.englishup.adaptors.datasources.local.ReadingLocalDataSource
import com.example.englishup.adaptors.datasources.remote.ReadingRemoteDataSource
import com.example.englishup.adaptors.mapper.ReadingMapper
import com.example.englishup.entities.Reading
import com.example.englishup.repositories.ReadingRepository
import javax.inject.Inject

class ReadingRepositoryImpl @Inject constructor(
    private val remoteDataSource: ReadingRemoteDataSource,
    private val localDataSource: ReadingLocalDataSource,
    private val mapper: ReadingMapper
) : ReadingRepository {

    private val TAG = "ReadingRepository"

    override suspend fun getAll(): List<Reading> {
        return try {
            Log.d(TAG, "getAll: Fetching from remote...")
            val remoteData = remoteDataSource.fetchReadingList()
            Log.d(TAG, "getAll: Fetched ${remoteData.size} items from remote")
            localDataSource.saveReadingList(remoteData)
            remoteData.map { mapper.toEntity(it) }
        } catch (e: Exception) {
            Log.e(TAG, "getAll: Error fetching from remote, falling back to local", e)
            val localData = localDataSource.getReadingList()
            Log.d(TAG, "getAll: Fetched ${localData.size} items from local")
            localData.map { mapper.toEntity(it) }
        }
    }

    override suspend fun getById(id: String): Reading? = null
    override suspend fun insert(item: Reading): Long = 0
    override suspend fun update(item: Reading) {}
    override suspend fun delete(item: Reading) {}
}
