package com.example.englishup.entities

data class Grammar(
    val id: String,
    val word: String,
    val translation: String,
    val phonetic: String
)

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
