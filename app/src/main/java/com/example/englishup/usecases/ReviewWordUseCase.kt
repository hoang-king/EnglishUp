package com.example.englishup.usecases

import android.util.Log
import com.example.englishup.entities.ReviewGrade
import com.example.englishup.entities.Vocabulary
import com.example.englishup.entities.ReviewLog
import com.example.englishup.entities.WordStatus
import com.example.englishup.repositories.VocabularyRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ReviewWordUseCase @Inject constructor(
    private val repository: VocabularyRepository
) {
    private val TAG = "ReviewWordUseCase"

    suspend fun execute(vocabulary: Vocabulary, grade: ReviewGrade) {
        val updated = calculateNextReview(vocabulary, grade)
        Log.d(TAG, "execute: Reviewing '${vocabulary.word}' with grade $grade. Next review: ${updated.nextReviewDate}")
        
        // Ghi log lịch sử
        val now = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val log = ReviewLog(
            id = "", // Sẽ được Room tự sinh
            wordId = vocabulary.id,
            word = vocabulary.word,
            grade = grade,
            timestamp = now,
            isCorrect = grade != ReviewGrade.AGAIN
        )
        repository.addReviewLog(log)
        
        repository.updateReviewSchedule(updated)
    }

    private fun calculateNextReview(vocabulary: Vocabulary, grade: ReviewGrade): Vocabulary {
        var ease = vocabulary.easeFactor
        var interval = vocabulary.intervalDays
        var repetitions = vocabulary.repetitions
        var status = vocabulary.status

        // Áp dụng thuật toán SM-2 (SuperMemo 2) theo yêu cầu người dùng
        when (grade) {
            ReviewGrade.AGAIN -> {
                // Quên hoàn toàn: Xuất hiện lại sau 1 phút (interval = 0 để hiện ngay trong ngày)
                ease = (ease - 0.2).coerceAtLeast(1.3)
                interval = 0 
                repetitions = 0
                status = WordStatus.LEARNING
            }
            ReviewGrade.HARD -> {
                // Nhớ nhưng phải cố: Interval tăng nhẹ (x1.2), ease giảm 0.15
                ease = (ease - 0.15).coerceAtLeast(1.3)
                interval = if (interval == 0) 1 else (interval * 1.2).toInt().coerceAtLeast(1)
                repetitions++
                status = WordStatus.REVIEW
            }
            ReviewGrade.GOOD -> {
                // Nhớ bình thường: Ease giữ nguyên, interval x Ease
                // Nếu lần đầu (interval=0), mặc định lên 1 ngày
                interval = if (interval == 0) 1 else (interval * ease).toInt().coerceAtLeast(1)
                repetitions++
                status = WordStatus.KNOWN
            }
            ReviewGrade.EASY -> {
                // Nhớ rất dễ: Ease tăng 0.15, interval x Ease x 1.3
                // Nếu lần đầu (interval=0), mặc định lên 2 ngày
                ease += 0.15
                interval = if (interval == 0) 2 else (interval * ease * 1.3).toInt().coerceAtLeast(1)
                repetitions++
                status = WordStatus.KNOWN
            }
        }

        // Tính toán ngày ôn tập tiếp theo
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, interval)
        val nextDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)

        return vocabulary.copy(
            easeFactor = ease,
            intervalDays = interval,
            repetitions = repetitions,
            nextReviewDate = nextDate,
            status = status
        )
    }
}
