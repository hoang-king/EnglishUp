package com.example.englishup.adaptors.datasources.remote

import com.example.englishup.adaptors.datasources.local.Dto.GrammarDto
import javax.inject.Inject

interface GrammarRemoteDataSource {
    suspend fun fetchGrammarList(): List<GrammarDto>
    suspend fun checkText(text: String): GrammarCheckResponse
}

class GrammarRemoteDataSourceImpl @Inject constructor(
    private val apiService: GrammarApiService
) : GrammarRemoteDataSource {
    override suspend fun fetchGrammarList(): List<GrammarDto> {
        // Trả về danh sách chủ điểm ngữ pháp TOEIC ngay lập tức
        return listOf(
            GrammarDto("1", "Present Simple", "Thì hiện tại đơn - Thường gặp trong Part 5", "[ˈprɛzənt ˈsɪmpəl]"),
            GrammarDto("2", "Past Simple", "Thì quá khứ đơn - Dấu hiệu: yesterday, last week", "[pæst ˈsɪmpəl]"),
            GrammarDto("3", "Present Perfect", "Thì hiện tại hoàn thành - Dấu hiệu: since, for", "[ˈprɛzənt ˈpɜrfɪkt]"),
            GrammarDto("4", "Passive Voice", "Câu bị động - Rất phổ biến trong TOEIC Reading", "[ˈpæsɪv vɔɪs]"),
            GrammarDto("5", "Conditionals (If)", "Câu điều kiện loại 1, 2, 3", "[kənˈdɪʃənəlz]"),
            GrammarDto("6", "Relative Clauses", "Mệnh đề quan hệ (who, whom, which, that)", "[ˈrɛlətɪv ˈklɔzɪz]"),
            GrammarDto("7", "Gerunds & Infinitives", "Danh động từ và Động từ nguyên mẫu (V-ing / To V)", "[ˈdʒɛrəndz ænd ɪnˈfɪnɪtɪvz]"),
            GrammarDto("8", "Conjunctions", "Liên từ (although, because, therefore, however)", "[kənˈdʒʌŋkʃənz]")
        )
    }

    override suspend fun checkText(text: String): GrammarCheckResponse {
        return try {
            apiService.checkGrammar(text)
        } catch (e: Exception) {
            GrammarCheckResponse(emptyList())
        }
    }
}
