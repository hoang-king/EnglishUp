package com.example.englishup.adaptors.repositories

import android.util.Log
import com.example.englishup.adaptors.datasources.remote.GrammarRemoteDataSource
import com.example.englishup.adaptors.datasources.local.Dao.GrammarDao
import com.example.englishup.adaptors.mapper.GrammarMapper
import com.example.englishup.entities.Grammar
import com.example.englishup.repositories.GrammarRepository
import javax.inject.Inject

class GrammarRepositoryImpl @Inject constructor(
    private val remoteDataSource: GrammarRemoteDataSource,
    private val grammarDao: GrammarDao
) : GrammarRepository {

    private val TAG = "GrammarRepository"

    override suspend fun getAll(): List<Grammar> {
        return try {
            Log.d(TAG, "getAll: Fetching from remote API...")
            val remoteData = remoteDataSource.fetchGrammarList()
            Log.d(TAG, "getAll: Fetched ${remoteData.size} items from remote API")
            grammarDao.insertAll(remoteData)
            remoteData.map { GrammarMapper(it).toEntity() }
        } catch (e: Exception) {
            Log.e(TAG, "getAll: Error fetching from remote, falling back to local database", e)
            val localData = grammarDao.getAll()
            Log.d(TAG, "getAll: Fetched ${localData.size} items from local database")
            localData.map { GrammarMapper(it).toEntity() }
        }
    }

    override suspend fun getById(id: String): Grammar? = null
    override suspend fun insert(item: Grammar): Long = 0
    override suspend fun update(item: Grammar) {}
    override suspend fun delete(item: Grammar) {}
}
