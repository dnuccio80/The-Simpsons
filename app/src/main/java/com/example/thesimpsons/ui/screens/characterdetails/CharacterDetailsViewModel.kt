package com.example.thesimpsons.ui.screens.characterdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesimpsons.domain.repository.DataRepository
import com.example.thesimpsons.domain.usecases.user_preferences.GetDarkMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsViewModel @Inject constructor(
    private val repository: DataRepository,
    getDarkMode: GetDarkMode,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val characterId = savedStateHandle.getStateFlow("characterId", -1)

    private val darkMode =
        getDarkMode().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val character = characterId
        .filter { it != -1 }
        .flatMapLatest { id ->
            repository.getSingleCharacter(id)
        }

    val uiState: StateFlow<UiState> = combine(character, darkMode) { character, darkMode ->
        UiState.Success(character, darkMode) as UiState
    }
        .onStart { emit(UiState.Loading) }
        .catch { emit(UiState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)


    fun setCharacterId(id: Int) {
        savedStateHandle["characterId"] = id
    }


}