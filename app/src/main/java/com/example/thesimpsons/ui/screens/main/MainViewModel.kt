package com.example.thesimpsons.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesimpsons.domain.usecases.user_preferences.GetDarkMode
import com.example.thesimpsons.domain.usecases.user_preferences.GetFirstAccess
import com.example.thesimpsons.domain.usecases.user_preferences.GetUsername
import com.example.thesimpsons.domain.usecases.user_preferences.SetDarkMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getFirstAccess: GetFirstAccess,
    getUsername: GetUsername,
    getDarkMode: GetDarkMode,
    private val setDarkMode: SetDarkMode
) : ViewModel() {


    private val username: StateFlow<String> =
        getUsername().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
    private val isFirstAccess: StateFlow<Boolean?> =
        getFirstAccess().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    private val darkMode = getDarkMode().stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val uiState = combine(username, isFirstAccess,darkMode) { name, first, darkMode ->
        if (first != null) {
            MainUiState.Success(first, name, darkMode)
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

    fun toggleDarkMode() {
        viewModelScope.launch(Dispatchers.IO) {
            setDarkMode(!darkMode.value)
        }
    }


}

sealed class MainUiState {
    data object Loading : MainUiState()
    data class Success(val firstAccess: Boolean, val username: String, val darkMode: Boolean) : MainUiState()
    data class Error(val error: Exception) : MainUiState()
}