# EnglishUp — Chi tiết luồng từng module

> Clean Architecture | Android Kotlin + Jetpack Compose  
> Package: `com.englishup`

---

## Mục lục

1. [Module Vocabulary](#1-module-vocabulary)
2. [Module Grammar](#2-module-grammar)
3. [Module Quiz](#3-module-quiz)
4. [Module Listening](#4-module-listening)
5. [Module Progress](#5-module-progress)

---

## 1. Module Vocabulary

### Mục tiêu
Học từ vựng theo phương pháp Spaced Repetition System (SRS) — flashcard tự động lên lịch ôn dựa trên mức độ ghi nhớ.

---



### Luồng dữ liệu

```
VocabScreen (Compose)
    │
    │  collectAsState()
    ▼
VocabViewModel
    │  state: VocabUiState
    │    ├── allWords: List<Word>
    │    ├── dueWords: List<Word>
    │    ├── currentCard: Word?
    │    ├── cardIndex: Int
    │    └── isFlipped: Boolean
    │
    ├── GetWordsUseCase.execute()
    │       └── WordRepository.getWordsByStatus("all") → Flow<List<Word>>
    │
    ├── GetWordsDueForReviewUseCase.execute()
    │       └── WordRepository.getWordsDueForReview() → Flow<List<Word>>
    │               └── WHERE next_review_date <= today
    │
    ├── ReviewWordUseCase.execute(word, grade)
    │       └── SM-2 Algorithm:
    │             AGAIN → ease - 0.20, interval = 1
    │             HARD  → ease - 0.15, interval = interval × 1.2
    │             GOOD  → ease giữ nguyên, interval = interval × ease
    │             EASY  → ease + 0.15, interval = interval × ease × 1.3
    │               └── WordRepository.updateReviewSchedule(id, ease, interval, nextDate)
    │
    └── SearchWordUseCase.execute(query)
            └── WordRepository.searchWords(query) → List<Word>
```

---

### Luồng màn hình

```
VocabScreen
    │
    ├── Tab "Tất cả"
    │       └── WordListView(allWords)
    │             └── ListItem: word | definitionVi | status badge
    │
    ├── Tab "Cần ôn"
    │       └── FlashCardView(currentCard, isFlipped)
    │             │
    │             ├── Mặt trước: word + phonetic
    │             │     └── onClick → viewModel.flipCard()
    │             │
    │             └── Mặt sau: definitionVi + example
    │                   └── Hiện 4 nút: Again | Hard | Good | Easy
    │                         └── onClick → viewModel.reviewCard(grade)
    │                               ├── ReviewWordUseCase → cập nhật DB
    │                               └── cardIndex++ → load currentCard tiếp theo
    │
    └── Tab "Đã nhớ"
            └── WordListView(filter: status == KNOWN)
```

---

### Entities & Enums

```kotlin
data class Word(
    val id: String,
    val word: String,
    val phonetic: String,
    val partOfSpeech: String,
    val definitionEn: String,
    val definitionVi: String,
    val example: String,
    val audioUrl: String,
    val category: String,           // "TOEIC" | "IELTS" | "Business"
    val easeFactor: Double = 2.5,   // SRS ease factor
    val intervalDays: Int = 1,      // Số ngày đến lần ôn tiếp
    val repetitions: Int = 0,
    val nextReviewDate: String = "",
    val status: WordStatus = WordStatus.NEW
)

enum class WordStatus { NEW, LEARNING, REVIEW, KNOWN }
enum class ReviewGrade { AGAIN, HARD, GOOD, EASY }
```

---

### Dependency flow

```
VocabScreen
    └── VocabViewModel
          ├── GetWordsUseCase
          │     └── WordRepository (interface)
          │           └── WordRepositoryImpl
          │                 └── WordDataSource (Room DAO)
          ├── GetWordsDueForReviewUseCase
          │     └── WordRepository
          ├── ReviewWordUseCase (tự tính SRS, gọi repo)
          │     └── WordRepository
          └── SearchWordUseCase
                └── WordRepository
```

---

---

## 2. Module Grammar

### Mục tiêu
Học ngữ pháp theo chủ đề — mỗi chủ đề có nhiều bài học, mỗi bài có bài tập trắc nghiệm kèm giải thích.

---



### Luồng dữ liệu

```
GrammarTopicScreen
    │  collectAsState()
    ▼
GrammarViewModel
    │  state: GrammarUiState
    │    ├── topics: List<GrammarTopic>
    │    ├── selectedTopic: GrammarTopic?
    │    ├── lessons: List<GrammarLesson>
    │    ├── exercises: List<GrammarExercise>
    │    └── currentExercise: GrammarExercise?
    │
    ├── GetGrammarTopicsUseCase.execute()
    │       └── GrammarRepository.getAllTopics() → Flow<List<GrammarTopic>>
    │
    ├── GetLessonsByTopicUseCase.execute(topicId)
    │       └── GrammarRepository.getLessonsByTopic(topicId)
    │
    ├── GetExercisesUseCase.execute(lessonId)
    │       └── GrammarRepository.getExercisesByLesson(lessonId)
    │
    └── UpdateTopicProgressUseCase.execute(topicId, completedLessons)
            └── GrammarProgressRepository.updateProgress(topicId, count)
```

---

### Luồng màn hình

```
GrammarTopicScreen (Danh sách chủ đề)
    │
    ├── Tab "TOEIC"  /  Tab "IELTS"  /  Tab "Cần ôn"
    │
    └── Mỗi GrammarTopicCard:
          ├── Tên chủ đề (VN + EN)
          ├── Progress bar: completedLessons / totalLessons
          ├── % hoàn thành
          └── onClick → navigate GrammarLessonScreen(topicId)

GrammarLessonScreen (Bài học)
    │
    ├── Phần lý thuyết: text + ví dụ highlight
    │
    └── Phần bài tập:
          ├── Câu hỏi fill-in-the-blank hoặc MCQ
          ├── Người dùng chọn đáp án
          ├── Hiện đúng/sai + màu highlight
          ├── Hiện giải thích ngữ pháp
          └── Nút "Câu tiếp" → UpdateTopicProgressUseCase
```

---

### Entities

```kotlin
data class GrammarTopic(
    val id: String,
    val name: String,             // "Thì động từ"
    val nameEn: String,           // "Verb Tenses"
    val description: String,
    val totalLessons: Int,
    val completedLessons: Int,
    val accuracy: Float = 0f
)

data class GrammarLesson(
    val id: String,
    val topicId: String,
    val title: String,
    val theory: String,           // Nội dung lý thuyết
    val examples: List<String>,
    val order: Int
)

data class GrammarExercise(
    val id: String,
    val lessonId: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String,
    val type: ExerciseType        // FILL_BLANK | MULTIPLE_CHOICE | ERROR_CORRECTION
)

enum class ExerciseType { FILL_BLANK, MULTIPLE_CHOICE, ERROR_CORRECTION }
```

---

### Dependency flow

```
GrammarTopicScreen / GrammarLessonScreen
    └── GrammarViewModel
          ├── GetGrammarTopicsUseCase    → GrammarRepository → GrammarDataSource (Room)
          ├── GetLessonsByTopicUseCase   → GrammarRepository
          ├── GetExercisesUseCase        → GrammarRepository
          └── UpdateTopicProgressUseCase → GrammarProgressRepository → Room DB
```

---

---

## 3. Module Quiz

### Mục tiêu
Engine quiz dùng chung cho cả 4 kỹ năng — tải câu hỏi, chấm điểm, hiện giải thích, ghi kết quả vào Progress.

---



---

### Luồng dữ liệu

```
QuizScreen
    │  collectAsState()
    ▼
QuizViewModel
    │  state: QuizUiState
    │    ├── session: QuizSession?
    │    │     ├── questions: List<Question>
    │    │     ├── correctCount: Int
    │    │     ├── wrongCount: Int
    │    │     ├── currentIndex: Int
    │    │     └── isFinished: Boolean
    │    ├── currentQuestion: Question?
    │    ├── selectedAnswer: String?
    │    ├── answerResult: AnswerResult?
    │    └── isAnswered: Boolean
    │
    ├── startSession(skill, limit)
    │       └── GetQuestionsUseCase.execute(skill, limit)
    │               └── QuizRepository.getQuestions(skill, limit)
    │                     ├── Room DB (offline-first)
    │                     └── Open Trivia API (fallback nếu có mạng)
    │
    ├── submitAnswer(answer)
    │       └── CheckAnswerUseCase.execute(question, answer)
    │               └── AnswerResult(isCorrect, correctAnswer, explanation)
    │                     ├── isCorrect == true → correctCount++
    │                     └── isCorrect == false → wrongCount++
    │
    ├── nextQuestion()
    │       ├── currentIndex < questions.size → load câu tiếp
    │       └── currentIndex >= questions.size → isFinished = true
    │
    └── [Kết thúc phiên] → gọi ProgressViewModel.saveResult(skill, correct, wrong)
```

---

### Luồng màn hình

```
QuizScreen
    │
    ├── isLoading == true → CircularProgressIndicator
    │
    ├── isFinished == true → QuizResultScreen
    │       ├── Hiện % accuracy (xanh ≥75%, đỏ <75%)
    │       ├── Đúng / Sai / Tổng
    │       └── Nút "Làm lại" → startSession()
    │
    └── Đang làm bài:
          ├── LinearProgressIndicator: currentIndex / total
          ├── Counter: "3/10 câu  ✓2  ✗1"
          ├── Card câu hỏi: question.content
          ├── 4 OptionButton (A/B/C/D)
          │     ├── Chưa trả lời: màu neutral
          │     ├── Đáp án đúng: màu xanh (#00E5A0)
          │     └── Đáp án sai đã chọn: màu đỏ (#FF4D6A)
          ├── [Sau khi trả lời] ExplanationCard:
          │     └── question.explanation
          └── Nút "Câu tiếp →" → nextQuestion()
```

---

### Entities

```kotlin
data class Question(
    val id: String,
    val content: String,
    val options: List<String>,       // ["A. ...", "B. ...", "C. ...", "D. ..."]
    val correctAnswer: String,
    val explanation: String,
    val skill: QuizSkill,
    val difficulty: Difficulty,
    val category: String
)

data class QuizSession(
    val id: String,
    val skill: QuizSkill,
    val questions: List<Question>,
    val correctCount: Int = 0,
    val wrongCount: Int = 0,
    val currentIndex: Int = 0,
    val isFinished: Boolean = false
) {
    val accuracy: Float get() =
        if (correctCount + wrongCount > 0)
            correctCount * 100f / (correctCount + wrongCount)
        else 0f
}

data class AnswerResult(
    val isCorrect: Boolean,
    val correctAnswer: String,
    val explanation: String
)

enum class QuizSkill    { VOCABULARY, GRAMMAR, READING, LISTENING }
enum class Difficulty   { EASY, MEDIUM, HARD }
```

---

### Dependency flow

```
QuizScreen
    └── QuizViewModel
          ├── GetQuestionsUseCase
          │     └── QuizRepository (interface)
          │           └── QuizRepositoryImpl
          │                 ├── RoomQuestionDataSource   (offline)
          │                 └── ApiQuestionDataSource    (online: opentdb.com)
          └── CheckAnswerUseCase (pure logic, không cần repo)
```

---

---

## 4. Module Listening

### Mục tiêu
Luyện nghe theo bài audio TOEIC Part 1–4 / IELTS Listening — phát audio, dictation, trả lời câu hỏi.

---



### Luồng dữ liệu

```
ListeningScreen
    │  collectAsState()
    ▼
ListeningViewModel
    │  state: ListeningUiState
    │    ├── tracks: List<AudioTrack>
    │    ├── currentTrack: AudioTrack?
    │    ├── isPlaying: Boolean
    │    ├── positionSec: Int
    │    ├── durationSec: Int
    │    ├── dictationText: String
    │    ├── questions: List<ListeningQuestion>
    │    ├── selectedAnswers: Map<String, String>
    │    └── isSubmitted: Boolean
    │
    ├── GetTracksByPartUseCase.execute(part)
    │       └── ListeningRepository.getTracksByPart(part) → Flow<List<AudioTrack>>
    │
    ├── selectTrack(track)
    │       └── GetTrackDetailUseCase.execute(trackId)
    │               └── load questions kèm theo track
    │
    ├── togglePlay()
    │       └── AudioPlayerService.play() / pause()
    │               └── Cập nhật positionSec mỗi giây qua Flow
    │
    ├── updateDictation(text)
    │       └── _state.value.copy(dictationText = text)
    │
    ├── submitDictation()
    │       └── CheckDictationUseCase.execute(userText, transcript)
    │               └── So khớp từng từ → DictationResult(score, wrongWords)
    │
    └── submitAnswers()
            └── CheckListeningAnswerUseCase.execute(questions, selectedAnswers)
                    └── Đếm đúng/sai → gọi ProgressViewModel.saveResult("listening", ...)
```

---

### Luồng màn hình

```
ListeningScreen
    │
    ├── Tab "Part 1"  /  "Part 2"  /  "Part 3"  /  "Part 4"
    │
    ├── TrackList: danh sách AudioTrack
    │     └── onClick → selectTrack(track)
    │
    ├── AudioPlayerCard (khi đã chọn track)
    │     ├── Title + description
    │     ├── Waveform visualizer
    │     ├── Thanh thời gian: positionSec / durationSec
    │     └── Controls: ⏮ ↩10s ▶/⏸ ↪10s ⏭
    │           └── togglePlay() → AudioPlayerService
    │
    ├── DictationInput
    │     ├── TextField: "Nhập những gì bạn nghe được..."
    │     ├── Nút "Kiểm tra" → submitDictation()
    │     └── Kết quả: highlight từ sai màu đỏ, từ đúng màu xanh
    │
    └── ListeningQuestions (sau khi nghe xong)
          ├── Câu 1, 2, 3 (MCQ)
          ├── Chọn đáp án → selectedAnswers[questionId] = answer
          └── Nút "Nộp bài" → submitAnswers()
                └── Hiện đúng/sai + giải thích
```

---

### Entities

```kotlin
data class AudioTrack(
    val id: String,
    val title: String,
    val description: String,
    val audioUrl: String,
    val transcript: String,
    val durationSec: Int,
    val part: Int,               // TOEIC: 1-4 | IELTS: 1-4
    val questions: List<ListeningQuestion> = emptyList()
)

data class ListeningQuestion(
    val id: String,
    val trackId: String,
    val content: String,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String
)

data class DictationResult(
    val userText: String,
    val correctText: String,
    val score: Float,            // 0.0 – 100.0
    val wrongWords: List<String>
)
```

---

### Dependency flow

```
ListeningScreen
    └── ListeningViewModel
          ├── GetTracksByPartUseCase → ListeningRepository → ListeningDataSource (Room)
          ├── GetTrackDetailUseCase  → ListeningRepository
          ├── CheckDictationUseCase  (pure logic: so sánh text)
          ├── CheckListeningAnswerUseCase (pure logic: đếm đúng/sai)
          └── AudioPlayerService (Android Service, không phải domain)
```

---

---

## 5. Module Progress

### Mục tiêu
Theo dõi kết quả học tập từng ngày — đúng bao nhiêu, sai bao nhiêu, accuracy theo kỹ năng, streak liên tiếp.

---



### Luồng dữ liệu

```
ProgressScreen
    │  collectAsState()
    ▼
ProgressViewModel
    │  state: ProgressUiState
    │    ├── weeklySummary: List<DailySummary>    (7 ngày)
    │    ├── todayResult: DailySummary?
    │    ├── skillAverage: List<SkillAverage>
    │    ├── monthlyHistory: List<DailyResult>
    │    ├── streakDays: Int
    │    └── selectedDaySummary: DailySummary?
    │
    ├── GetWeeklySummaryUseCase.execute()
    │       └── ProgressRepository.getWeeklySummary()
    │               └── Room SQL:
    │                   SELECT date, SUM(correct), SUM(wrong),
    │                   ROUND(SUM(correct)*100.0/SUM(total),1) AS accuracy
    │                   FROM daily_results
    │                   GROUP BY date ORDER BY date DESC LIMIT 7
    │
    ├── GetSkillAverageUseCase.execute()
    │       └── ProgressRepository.getWeeklySkillAverage()
    │               └── Room SQL:
    │                   SELECT skill, ROUND(AVG(accuracy),1) AS avgAccuracy
    │                   FROM daily_results
    │                   WHERE date >= date('now','-7 days')
    │                   GROUP BY skill
    │
    ├── GetStreakUseCase.execute()
    │       └── ProgressRepository.getStreakCount()
    │               └── Đếm ngày liên tiếp có total > 0
    │
    ├── selectDay(summary)
    │       └── _state.value.copy(selectedDaySummary = summary)
    │
    └── saveResult(skill, correct, wrong, durationSec)
            └── SaveQuizResultUseCase.execute(skill, correct, wrong)
                    └── ProgressRepository.saveResult(DailyResult(...))
                          └── Room INSERT INTO daily_results
```

---

### Luồng màn hình

```
ProgressScreen
    │
    ├── Tab "Tổng quan"
    │     ├── StatCards hàng ngang (2×2):
    │     │     Đúng hôm nay | Sai hôm nay | Accuracy | Streak
    │     │
    │     ├── WeeklyBarChart (7 cột)
    │     │     ├── Mỗi cột = 1 ngày: bar xanh (đúng) + bar đỏ (sai)
    │     │     └── onClick vào cột → selectDay(summary)
    │     │
    │     └── DayDetailCard (ngày được chọn)
    │           ├── Header: date + accuracy badge (xanh/vàng/đỏ)
    │           ├── 3 box: Đúng | Sai | Tổng
    │           └── SkillDetail: 4 progress bar (Vocab/Grammar/Reading/Listening)
    │
    ├── Tab "Theo ngày"
    │     └── DailyLogList
    │           └── Mỗi row: date | bar đúng + bar sai | số đúng/sai | %
    │                 Màu %: xanh ≥75% | vàng 55–74% | đỏ <55% | xám 0%
    │
    ├── Tab "Kỹ năng"
    │     ├── SkillBreakdownCard (tuần này)
    │     │     └── 4 kỹ năng: icon + tên + progress bar + % + mũi tên tăng/giảm
    │     └── HardWordsList
    │           └── Từ bị sai nhiều nhất trong tuần (top 5)
    │
    └── Tab "Lịch streak"
          ├── StreakCalendar (heatmap 1 tháng)
          │     ├── Màu ô: xám = không học | xanh nhạt→đậm = ít→nhiều
          │     └── onClick ô → hiện tooltip: "30/3: 76% — 50 câu"
          └── MonthlyStatCards:
                Ngày học | Tổng câu | Avg đúng/ngày | Avg sai/ngày
```

---

### Entities

```kotlin
data class DailyResult(
    val id: Long = 0,
    val date: String,             // "2024-03-30"
    val skill: String,            // "vocabulary" | "grammar" | "reading" | "listening"
    val correct: Int,
    val wrong: Int,
    val total: Int,
    val accuracy: Float,          // 0.0 – 100.0
    val durationSec: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

data class DailySummary(
    val date: String,
    val correct: Int,
    val wrong: Int,
    val total: Int,
    val accuracy: Float
)

data class SkillAverage(
    val skill: String,
    val avgAccuracy: Float,
    val totalCorrect: Int,
    val totalWrong: Int
)
```

---

### Room SQL queries

```sql
-- Tổng hợp 7 ngày
SELECT date,
       SUM(correct)  AS correct,
       SUM(wrong)    AS wrong,
       SUM(total)    AS total,
       ROUND(SUM(correct)*100.0/SUM(total), 1) AS accuracy
FROM   daily_results
GROUP  BY date
ORDER  BY date DESC
LIMIT  7;

-- Trung bình kỹ năng trong tuần
SELECT skill,
       ROUND(AVG(accuracy), 1)  AS avgAccuracy,
       SUM(correct)             AS totalCorrect,
       SUM(wrong)               AS totalWrong
FROM   daily_results
WHERE  date >= date('now', '-7 days')
GROUP  BY skill;

-- Lịch sử tháng hiện tại
SELECT * FROM daily_results
WHERE  date LIKE '2024-03%'
ORDER  BY date DESC;

-- Streak: đếm ngày học liên tiếp
SELECT COUNT(DISTINCT date) AS streak
FROM   daily_results
WHERE  total > 0
AND    date >= date('now', '-30 days');
```

---

### Dependency flow

```
ProgressScreen
    └── ProgressViewModel
          ├── GetWeeklySummaryUseCase
          │     └── ProgressRepository (interface)
          │           └── ProgressRepositoryImpl
          │                 └── ProgressDataSource (Room DAO)
          ├── GetSkillAverageUseCase  → ProgressRepository
          ├── GetStreakUseCase        → ProgressRepository
          └── SaveQuizResultUseCase  → ProgressRepository → Room INSERT
```

---

### Logic màu accuracy

```kotlin
fun accuracyColor(accuracy: Float): Color = when {
    accuracy >= 75f -> Color(0xFF00E5A0)  // Xanh  — Tốt
    accuracy >= 55f -> Color(0xFFFFB547)  // Vàng  — Trung bình
    accuracy > 0f   -> Color(0xFFFF4D6A)  // Đỏ    — Cần cải thiện
    else            -> Color(0xFF4A5A7A)  // Xám   — Không học
}

fun accuracyLabel(accuracy: Float): String = when {
    accuracy >= 75f -> "Tốt"
    accuracy >= 55f -> "Trung bình"
    accuracy > 0f   -> "Cần ôn"
    else            -> "Không học"
}
```

---

## Quy tắc chung (áp dụng cho tất cả module)

| Quy tắc | Mô tả |
|---|---|
| Offline-first | Luôn đọc Room DB trước, API chỉ là nguồn bổ sung |
| Single UseCase | Mỗi UseCase chỉ làm 1 việc duy nhất |
| No Android in Domain | Entity, Repository interface, UseCase không import Android SDK |
| StateFlow | ViewModel expose StateFlow, UI collect bằng `collectAsStateWithLifecycle()` |
| Mapper bắt buộc | DTO → Entity luôn qua Mapper, không map trực tiếp trong Repo |
| Constructor injection | Không dùng singleton, inject qua constructor hoặc Hilt |

---

*Tài liệu cập nhật: 2024-03-31 | Version: 1.0.0*