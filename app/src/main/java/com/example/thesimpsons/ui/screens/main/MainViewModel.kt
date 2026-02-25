package com.example.thesimpsons.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesimpsons.domain.usecases.user_preferences.GetFirstAccess
import com.example.thesimpsons.domain.usecases.user_preferences.GetUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getFirstAccess: GetFirstAccess,
    getUsername: GetUsername,
) : ViewModel() {


    private val username: StateFlow<String> =
        getUsername().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
    private val isFirstAccess: StateFlow<Boolean?> =
        getFirstAccess().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val uiState = combine(username, isFirstAccess) { name, first ->
        if (first != null) {
            MainUiState.Success(first, name)
        } else {
            MainUiState.Loading
        }
    }.catch { e ->
        emit(MainUiState.Error(e as Exception))
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        MainUiState.Loading
    )
}

sealed class MainUiState {
    data object Loading : MainUiState()
    data class Success(val firstAccess: Boolean, val username: String) : MainUiState()
    data class Error(val error: Exception) : MainUiState()
}