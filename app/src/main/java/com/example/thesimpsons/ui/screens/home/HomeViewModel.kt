package com.example.thesimpsons.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesimpsons.data.RepositoryImpl
import com.example.thesimpsons.domain.CharacterDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RepositoryImpl):ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState:StateFlow<UiState> = _uiState

    fun getSingleCharacter() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val character = repository.getSingleCharacter(15).first()
                _uiState.value = UiState.Success(character)
            }catch (e:Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }
}

sealed class UiState {
    data class Success(val character: CharacterDomain):UiState()
    data class Error(val error:Throwable):UiState()
    data object Loading:UiState()
}