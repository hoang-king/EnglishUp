package com.example.englishup.adaptors.datasources.local

import com.example.englishup.adaptors.datasources.local.Dto.GrammarDto
import javax.inject.Inject

interface GrammarLocalDataSource {
    suspend fun getGrammarList(): List<GrammarDto>
    suspend fun saveGrammarList(list: List<GrammarDto>)
}

class GrammarLocalDataSourceImpl @Inject constructor() : GrammarLocalDataSource {
    private var cache = mutableListOf<GrammarDto>()

    init {
        // Khởi tạo 10 câu hỏi mẫu cho phần "Thì động từ"
        val questions = listOf(
            GrammarDto(
                id = "1", category = "Thì động từ",
                question = "By the time we arrived, the movie ______ for 15 minutes.",
                context = "Talking about a movie session in the past.",
                optionA = "had been playing", optionB = "is playing",
                optionC = "has played", optionD = "plays",
                correctAnswer = "A",
                explanation = "Dùng Quá khứ hoàn thành tiếp diễn để diễn tả một hành động đã xảy ra và kéo dài liên tục trước một thời điểm hoặc một hành động khác trong quá khứ."
            ),
            GrammarDto(
                id = "2", category = "Thì động từ",
                question = "I ______ English since I was 10 years old.",
                context = "Describing a continuous learning process until now.",
                optionA = "am learning", optionB = "have been learning",
                optionC = "learned", optionD = "learn",
                correctAnswer = "B",
                explanation = "Dùng Hiện tại hoàn thành tiếp diễn để nhấn mạnh tính liên tục của hành động bắt đầu trong quá khứ và kéo dài đến hiện tại."
            ),
            GrammarDto(
                id = "3", category = "Thì động từ",
                question = "The marketing team ______ on the new campaign for three weeks before the CEO approved it.",
                context = "Business email discussing project timeline.",
                optionA = "is working", optionB = "had been working",
                optionC = "has worked", optionD = "will have worked",
                correctAnswer = "B",
                explanation = "Dùng Past Perfect Continuous vì hành động kéo dài ('three weeks') trước một sự kiện trong quá khứ ('CEO approved'). Cấu trúc: had been + V-ing."
            ),
            GrammarDto(
                id = "4", category = "Thì động từ",
                question = "Listen! Someone ______ the piano upstairs.",
                context = "Reaction to a current sound.",
                optionA = "plays", optionB = "is playing",
                optionC = "has played", optionD = "was playing",
                correctAnswer = "B",
                explanation = "Dùng Hiện tại tiếp diễn cho hành động đang diễn ra tại thời điểm nói."
            ),
            GrammarDto(
                id = "5", category = "Thì động từ",
                question = "Next year, they ______ married for 20 years.",
                context = "Looking forward to an anniversary.",
                optionA = "will be", optionB = "will have been",
                optionC = "have been", optionD = "are being",
                correctAnswer = "B",
                explanation = "Dùng Tương lai hoàn thành để diễn tả một hành động sẽ hoàn thành vào một thời điểm nhất định trong tương lai."
            ),
            GrammarDto(
                id = "6", category = "Thì động từ",
                question = "Water ______ at 100 degrees Celsius.",
                context = "Scientific fact.",
                optionA = "boils", optionB = "is boiling",
                optionC = "boiled", optionD = "will boil",
                correctAnswer = "A",
                explanation = "Dùng Hiện tại đơn để diễn tả một sự thật hiển nhiên hoặc chân lý."
            ),
            GrammarDto(
                id = "7", category = "Thì động từ",
                question = "I ______ my homework when the lights went out.",
                context = "Describing an interrupted past action.",
                optionA = "do", optionB = "was doing",
                optionC = "have done", optionD = "did",
                correctAnswer = "B",
                explanation = "Dùng Quá khứ tiếp diễn cho một hành động đang diễn ra thì có một hành động khác xen vào (dùng Quá khứ đơn)."
            ),
            GrammarDto(
                id = "8", category = "Thì động từ",
                question = "She ______ her report already.",
                context = "Status of a task.",
                optionA = "finished", optionB = "has finished",
                optionC = "is finishing", optionD = "finish",
                correctAnswer = "B",
                explanation = "Dùng Hiện tại hoàn thành với 'already' để chỉ một hành động vừa mới hoàn thành."
            ),
            GrammarDto(
                id = "9", category = "Thì động từ",
                question = "Look at those clouds! It ______ rain.",
                context = "Prediction based on current evidence.",
                optionA = "is going to", optionB = "will",
                optionC = "rains", optionD = "is raining",
                correctAnswer = "A",
                explanation = "Dùng 'be going to' cho một dự đoán dựa trên bằng chứng thực tế ở hiện tại."
            ),
            GrammarDto(
                id = "10", category = "Thì động từ",
                question = "I think he ______ the job soon.",
                context = "Personal opinion about the future.",
                optionA = "gets", optionB = "will get",
                optionC = "is getting", optionD = "got",
                correctAnswer = "B",
                explanation = "Dùng 'will' cho một dự đoán mang tính cá nhân hoặc không có căn cứ chắc chắn."
            )
        )
        cache.addAll(questions)
    }

    override suspend fun getGrammarList(): List<GrammarDto> {
        return cache
    }

    override suspend fun saveGrammarList(list: List<GrammarDto>) {
        cache.clear()
        cache.addAll(list)
    }
}
