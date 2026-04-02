package com.example.englishup.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishup.entities.Grammar
import com.example.englishup.usecases.GetGrammarListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrammarViewModel @Inject constructor(
    private val getGrammarListUseCase: GetGrammarListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<List<Grammar>>(emptyList())
    val state: StateFlow<List<Grammar>> = _state

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = getGrammarListUseCase.execute()
        }
    }
}
