package com.example.thesimpsons.ui.screens.characterdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesimpsons.data.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(private val repository: RepositoryImpl):ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState:StateFlow<UiState> = _uiState

    fun getCharacterDetails(characterId:Int) {
        viewModelScope.launch {
            try {
                val character = repository.getSingleCharacter(characterId)
                _uiState.value = UiState.Success(character.first())
            }catch (e:Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }


}