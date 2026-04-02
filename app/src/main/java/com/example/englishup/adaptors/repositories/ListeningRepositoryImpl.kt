package com.example.englishup.adaptors.repositories

import android.util.Log
import com.example.englishup.adaptors.datasources.local.ListeningLocalDataSource
import com.example.englishup.adaptors.datasources.remote.ListeningRemoteDataSource
import com.example.englishup.adaptors.mapper.ListeningMapper
import com.example.englishup.entities.Listening
import com.example.englishup.repositories.ListeningRepository
import javax.inject.Inject

class ListeningRepositoryImpl @Inject constructor(
    private val remoteDataSource: ListeningRemoteDataSource,
    private val localDataSource: ListeningLocalDataSource
) : ListeningRepository {

    private val TAG = "ListeningRepository"

    override suspend fun getAll(): List<Listening> {
        return try {
            Log.d(TAG, "getAll: Fetching from remote...")
            val remoteData = remoteDataSource.fetchListeningList()
            Log.d(TAG, "getAll: Fetched ${remoteData.size} items from remote")
            localDataSource.saveListeningList(remoteData)
            remoteData.map { ListeningMapper(it).toEntity() }
        } catch (e: Exception) {
            Log.e(TAG, "getAll: Error fetching from remote, falling back to local", e)
            val localData = localDataSource.getListeningList()
            Log.d(TAG, "getAll: Fetched ${localData.size} items from local")
            localData.map { ListeningMapper(it).toEntity() }
        }
    }

    override suspend fun getById(id: String): Listening? = null
    override suspend fun insert(item: Listening): Long = 0
    override suspend fun update(item: Listening) {}
    override suspend fun delete(item: Listening) {}
}

