package com.example.thesimpsons.ui.screens.episodedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesimpsons.data.RepositoryImpl
import com.example.thesimpsons.domain.EpisodeDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(val repository: RepositoryImpl):ViewModel() {

    private val _uiState:MutableStateFlow<EpisodeUiState> = MutableStateFlow(EpisodeUiState.Loading)
    val uiState:StateFlow<EpisodeUiState> = _uiState

    fun getEpisodeInfo(id:Int) {
        viewModelScope.launch {
            try {
                val episode = repository.getSingleEpisode(id)
                _uiState.value = EpisodeUiState.Success(episode.first())
            } catch (e:Exception) {
                _uiState.value = EpisodeUiState.Error(e)
            }
        }
    }

}

sealed class EpisodeUiState() {
    data object Loading:EpisodeUiState()
    data class Success(val episode:EpisodeDomain):EpisodeUiState()
    data class Error(val error:Exception):EpisodeUiState()
}