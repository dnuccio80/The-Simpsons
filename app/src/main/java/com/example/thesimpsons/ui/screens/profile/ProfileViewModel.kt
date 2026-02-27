package com.example.thesimpsons.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesimpsons.domain.usecases.user_preferences.GetUsername
import com.example.thesimpsons.domain.usecases.user_preferences.SetUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getUsername: GetUsername,
    private val setUsername: SetUsername,
) : ViewModel() {

    private val username = getUsername().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val uiState: StateFlow<ProfileUIState> = username.map { nickName ->
        ProfileUIState.Success(nickName) as ProfileUIState
    }
        .onStart {
            emit(ProfileUIState.Loading)
        }
        .catch { emit(ProfileUIState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileUIState.Loading)

    fun setNewUsername(nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setUsername(nickName)
        }
    }

}

sealed class ProfileUIState {
    data class Success(val username: String) : ProfileUIState()
    data object Loading : ProfileUIState()
    data class Error(val error: Throwable) : ProfileUIState()
}