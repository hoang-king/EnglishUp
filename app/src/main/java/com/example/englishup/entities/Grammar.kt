package com.example.englishup.entities

data class Grammar(
    val id: String,
    val category: String,         // Ví dụ: "Thì động từ"
    val question: String,         // Nội dung câu hỏi
    val context: String,          // Ngữ cảnh (ví dụ: "Business email...")
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String,    // "A", "B", "C", hoặc "D"
    val explanation: String       // Phần giải thích
)
