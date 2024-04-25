package com.xloger.answers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xloger.answers.entity.MainUiState
import com.xloger.answers.repository.ChatGPTRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val chatRepository = ChatGPTRepository()

    private val _uiState = MutableStateFlow(MainUiState("", ""))
    val uiState = _uiState.asStateFlow()

    fun requestQuestion(question: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(question = question, answer = "") }
            val result = chatRepository.chat(question)
            _uiState.update { it.copy(answer = result) }
        }
    }
}