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
        return listOf(
            GrammarDto(
                id = "g1", category = "Verb Tenses",
                question = "By the time we arrived, the movie ______ for 15 minutes.",
                context = "Past Perfect Continuous usage.",
                optionA = "had been playing", optionB = "is playing", optionC = "has played", optionD = "plays",
                correctAnswer = "A", explanation = "Use Past Perfect Continuous for an action that started and continued up until another point in the past."
            ),
            GrammarDto(
                id = "g2", category = "Conditionals",
                question = "If I ______ you, I would take that job offer.",
                context = "Type 2 Conditional (unreal present).",
                optionA = "am", optionB = "was", optionC = "were", optionD = "be",
                correctAnswer = "C", explanation = "In Type 2 conditionals, 'were' is used for all subjects with the verb 'to be'."
            ),
            GrammarDto(
                id = "g3", category = "Relative Clauses",
                question = "The man ______ car was stolen reported it to the police.",
                context = "Possessive relative pronoun.",
                optionA = "who", optionB = "whom", optionC = "whose", optionD = "which",
                correctAnswer = "C", explanation = "'Whose' is used to show possession."
            ),
            GrammarDto(
                id = "g4", category = "Passive Voice",
                question = "The new bridge ______ by next year.",
                context = "Future Passive.",
                optionA = "will build", optionB = "will be built", optionC = "is building", optionD = "has built",
                correctAnswer = "B", explanation = "Future Passive structure: will + be + past participle."
            ),
            GrammarDto(
                id = "g5", category = "Modals",
                question = "You ______ smoke in the hospital; it's strictly forbidden.",
                context = "Prohibition.",
                optionA = "mustn't", optionB = "don't have to", optionC = "shouldn't", optionD = "might not",
                correctAnswer = "A", explanation = "'Mustn't' expresses strict prohibition."
            ),
            GrammarDto(
                id = "g6", category = "Gerunds & Infinitives",
                question = "I look forward to ______ from you soon.",
                context = "Phrasal verb followed by a gerund.",
                optionA = "hear", optionB = "hearing", optionC = "to hear", optionD = "heard",
                correctAnswer = "B", explanation = "'Look forward to' is followed by a gerund (-ing form)."
            ),
            GrammarDto(
                id = "g7", category = "Articles",
                question = "She is ______ honest woman.",
                context = "Indefinite article with vowel sound.",
                optionA = "a", optionB = "an", optionC = "the", optionD = "no article",
                correctAnswer = "B", explanation = "'Honest' starts with a silent 'h' and a vowel sound, so use 'an'."
            ),
            GrammarDto(
                id = "g8", category = "Conjunctions",
                question = "He failed the exam ______ he studied very hard.",
                context = "Contrast.",
                optionA = "because", optionB = "although", optionC = "so", optionD = "but",
                correctAnswer = "B", explanation = "'Although' is used to show contrast between two clauses."
            ),
            GrammarDto(
                id = "g9", category = "Reported Speech",
                question = "He said that he ______ busy that day.",
                context = "Backshifting in reported speech.",
                optionA = "is", optionB = "was", optionC = "will be", optionD = "has been",
                correctAnswer = "B", explanation = "Present simple 'is' changes to past simple 'was' in reported speech."
            ),
            GrammarDto(
                id = "g10", category = "Prepositions",
                question = "She is very good ______ playing the piano.",
                context = "Adjective + Preposition.",
                optionA = "at", optionB = "in", optionC = "on", optionD = "with",
                correctAnswer = "A", explanation = "The correct preposition after 'good' (meaning skillful) is 'at'."
            )
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
