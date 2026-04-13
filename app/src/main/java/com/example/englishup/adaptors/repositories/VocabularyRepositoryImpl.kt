package com.example.englishup.adaptors.repositories

import android.util.Log
import com.example.englishup.adaptors.datasources.local.Dao.ProgressDao
import com.example.englishup.adaptors.datasources.local.Dto.ProgressDto
import com.example.englishup.adaptors.datasources.local.VocabularyLocalDataSource
import com.example.englishup.adaptors.datasources.remote.VocabularyRemoteDataSource
import com.example.englishup.adaptors.mapper.VocabularyMapper
import com.example.englishup.entities.Vocabulary
import com.example.englishup.entities.ReviewLog
import com.example.englishup.repositories.VocabularyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class VocabularyRepositoryImpl @Inject constructor(
    private val remoteDataSource: VocabularyRemoteDataSource,
    private val localDataSource: VocabularyLocalDataSource,
    private val mapper: VocabularyMapper,
    private val progressDao: ProgressDao
) : VocabularyRepository {

    private val TAG = "VocabularyRepository"

    // Kho từ vựng phong phú (Lấy 10 từ/ngày từ đây)
    private val globalWordPool = listOf(
        "Ambition", "Benevolent", "Captivate", "Diligence", "Eloquent", "Frugal", "Generosity", "Harmony", "Impeccable", "Jubilant",
        "Knowledge", "Lucid", "Meticulous", "Novelty", "Optimistic", "Prudent", "Quintessential", "Resilient", "Sincere", "Tenacious",
        "Ubiquitous", "Vibrant", "Whimsical", "Xenial", "Yearning", "Zealous", "Abundant", "Brilliant", "Candid", "Decisive",
        "Empower", "Fortitude", "Gratitude", "Honesty", "Insightful", "Justice", "Kindness", "Loyalty", "Modest", "Noble",
        "Original", "Passion", "Quality", "Refinement", "Splendid", "Thriving", "Unique", "Valuable", "Wisdom", "Xenon",
        "Youthful", "Zenith", "Artistic", "Balanced", "Creative", "Dynamic", "Energetic", "Flexible", "Genuine", "Healthy",
        "Innovative", "Joyful", "Knowledgeable", "Logical", "Motivation", "Natural", "Orderly", "Peaceful", "Quiet", "Radiant",
        "Strong", "Thoughtful", "Useful", "Versatile", "Warm", "Excellent", "Young", "Zesty", "Action", "Believe", "Change", "Dream",
        "Effort", "Focus", "Growth", "Hope", "Imagine", "Journey", "Keep", "Learn", "Make", "Never", "Open", "Plan", "Question",
        "Rise", "Smile", "Try", "Understand", "Victory", "Work", "Xylophone", "Yes", "Zero",
        "Adept", "Bashful", "Coherent", "Defiance", "Eager", "Facetious", "Graceful", "Humble", "Immaculate", "Jealous",
        "Keen", "Luminous", "Melody", "Nimble", "Obscure", "Pensive", "Quaint", "Robust", "Serene", "Tranquil",
        "Uplift", "Vivid", "Wander", "Xerox", "Yield", "Zest", "Avenue", "Bridge", "Castle", "Desert",
        "Echo", "Forest", "Garden", "Island", "Jungle", "Lake", "Mountain", "Ocean", "Palace", "River",
        "Street", "Tunnel", "Valley", "Window", "Bridge", "Cloud", "Drum", "Earth", "Fire", "Glass"
    )

    override fun getAllFlow(): Flow<List<Vocabulary>> = flow {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        Log.d(TAG, "getAllFlow: Triggering flow collection for $today")
        
        try {
            // Kiểm tra xem hôm nay đã được nạp từ mới chưa bằng cách xem bảng progress
            val dailyProgress = progressDao.getByDate(today)

            if (dailyProgress == null) {
                Log.d(TAG, "getAllFlow: First run of the day ($today). Refilling 10 words...")
                
                val localData = localDataSource.getAll()
                // Lọc bỏ những từ đã có trong DB
                val existingWords = localData.map { it.word.lowercase() }.toSet()
                val newWordsToFetch = globalWordPool
                    .filter { it.lowercase() !in existingWords }
                    .take(10)

                if (newWordsToFetch.isNotEmpty()) {
                    Log.d(TAG, "getAllFlow: Fetching details for $newWordsToFetch from API...")
                    val fetchedDetails = remoteDataSource.fetchVocabularyDetails(newWordsToFetch)
                    
                    // Gán ngày ôn tập là hôm nay
                    val readyToSave = fetchedDetails.map { it.copy(nextReviewDate = today) }
                    localDataSource.saveVocabularyList(readyToSave)
                    
                    // Tạo entry cho ngày mới trong bảng progress để không nạp lại nữa
                    progressDao.insert(ProgressDto(today, today, 0, 0))
                    Log.d(TAG, "getAllFlow: Successfully added 10 new words for today.")
                } else {
                    // Ngay cả khi không có từ mới, cũng đánh dấu đã kiểm tra cho hôm nay
                    progressDao.insert(ProgressDto(today, today, 0, 0))
                }
            } else {
                Log.d(TAG, "getAllFlow: Already refilled for today ($today). Skipping refill.")
            }
        } catch (e: Exception) {
            Log.e(TAG, "getAllFlow: Error adding daily words", e)
        }
        
        // Phát dữ liệu từ database (Room)
        emitAll(localDataSource.getAllFlow().map { list ->
            list.map { mapper.toEntity(it) }
        })
    }

    override suspend fun getAll(): List<Vocabulary> {
        return localDataSource.getAll().map { mapper.toEntity(it) }
    }

    override fun getDueForReview(today: String): Flow<List<Vocabulary>> {
        return localDataSource.getDueForReview(today).map { list ->
            list.map { mapper.toEntity(it) }
        }
    }

    override suspend fun search(query: String): List<Vocabulary> {
        return localDataSource.search(query).map { mapper.toEntity(it) }
    }

    override suspend fun updateReviewSchedule(vocabulary: Vocabulary) {
        localDataSource.update(mapper.toDto(vocabulary))
    }

    override suspend fun addReviewLog(log: ReviewLog) {
        localDataSource.addReviewLog(mapper.toReviewLogDto(log))
    }

    override fun getReviewLogs(): Flow<List<ReviewLog>> {
        return localDataSource.getReviewLogs().map { list ->
            list.map { mapper.toReviewLogEntity(it) }
        }
    }

    override suspend fun getById(id: String): Vocabulary? = null
    override suspend fun insert(item: Vocabulary): Long = 0
    override suspend fun update(item: Vocabulary) {
        localDataSource.update(mapper.toDto(item))
    }
    override suspend fun delete(item: Vocabulary) {}
}
