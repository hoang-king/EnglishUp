package com.example.englishup.adaptors.datasources.local

import com.example.englishup.adaptors.datasources.local.Dao.VocabularyDao
import com.example.englishup.adaptors.datasources.local.Dao.ReviewLogDao
import com.example.englishup.adaptors.datasources.local.Dto.VocabularyDto
import com.example.englishup.adaptors.datasources.local.Dto.ReviewLogDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface VocabularyLocalDataSource {
    fun getAllFlow(): Flow<List<VocabularyDto>>
    suspend fun getAll(): List<VocabularyDto>
    suspend fun saveVocabularyList(list: List<VocabularyDto>)
    fun getDueForReview(today: String): Flow<List<VocabularyDto>>
    suspend fun search(query: String): List<VocabularyDto>
    suspend fun update(dto: VocabularyDto)
    
    suspend fun addReviewLog(log: ReviewLogDto)
    fun getReviewLogs(): Flow<List<ReviewLogDto>>
}

class VocabularyLocalDataSourceImpl @Inject constructor(
    private val vocabularyDao: VocabularyDao,
    private val reviewLogDao: ReviewLogDao
) : VocabularyLocalDataSource {

    override fun getAllFlow(): Flow<List<VocabularyDto>> = vocabularyDao.getAllFlow()

    override suspend fun getAll(): List<VocabularyDto> = vocabularyDao.getAll()

    override suspend fun saveVocabularyList(list: List<VocabularyDto>) {
        vocabularyDao.insertAll(list)
    }

    override fun getDueForReview(today: String): Flow<List<VocabularyDto>> = 
        vocabularyDao.getDueForReview(today)

    override suspend fun search(query: String): List<VocabularyDto> = 
        vocabularyDao.search(query)

    override suspend fun update(dto: VocabularyDto) {
        vocabularyDao.update(dto)
    }

    override suspend fun addReviewLog(log: ReviewLogDto) {
        reviewLogDao.insert(log)
    }

    override fun getReviewLogs(): Flow<List<ReviewLogDto>> = reviewLogDao.getAllFlow()
}
