package com.example.englishup.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishup.entities.ReviewGrade
import com.example.englishup.entities.Vocabulary
import com.example.englishup.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class VocabUiState(
    val allWords: List<Vocabulary> = emptyList(),
    val dueWords: List<Vocabulary> = emptyList(),
    val currentCard: Vocabulary? = null,
    val cardIndex: Int = 0,
    val isFlipped: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class VocabularyViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val getWordsDueForReviewUseCase: GetWordsDueForReviewUseCase,
    private val reviewWordUseCase: ReviewWordUseCase,
    private val searchWordUseCase: SearchWordUseCase
) : ViewModel() {

    private val TAG = "VocabularyViewModel"
    private val _state = MutableStateFlow(VocabUiState())
    val state: StateFlow<VocabUiState> = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        Log.d(TAG, "loadData: Start loading vocabulary for date: $today")
        
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            combine(
                getWordsUseCase.execute(),
                getWordsDueForReviewUseCase.execute(today)
            ) { all, due ->
                Log.d(TAG, "loadData: Received ${all.size} total words and ${due.size} due words")
                _state.update { it.copy(
                    allWords = all,
                    dueWords = due,
                    currentCard = if (due.isNotEmpty()) due[0] else null,
                    isLoading = false
                ) }
            }.collect()
        }
    }

    fun flipCard() {
        _state.update { it.copy(isFlipped = !it.isFlipped) }
    }

    fun selectWord(word: Vocabulary) {
        _state.update { it.copy(
            currentCard = word,
            isFlipped = false,
            cardIndex = _state.value.dueWords.indexOfFirst { it.id == word.id }.let { if (it == -1) 0 else it }
        ) }
    }

    fun reviewCard(grade: ReviewGrade) {
        val currentCard = _state.value.currentCard ?: return
        
        viewModelScope.launch {
            reviewWordUseCase.execute(currentCard, grade)
            
            val nextIndex = _state.value.cardIndex + 1
            val dueWords = _state.value.dueWords
            
            if (nextIndex < dueWords.size) {
                _state.update { it.copy(
                    cardIndex = nextIndex,
                    currentCard = dueWords[nextIndex],
                    isFlipped = false
                ) }
            } else {
                _state.update { it.copy(
                    cardIndex = 0,
                    currentCard = null,
                    isFlipped = false
                ) }
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            val results = searchWordUseCase.execute(query)
            // Handle search results if needed in UI state
        }
    }
}
