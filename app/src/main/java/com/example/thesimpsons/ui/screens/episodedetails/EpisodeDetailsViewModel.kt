package com.example.thesimpsons.ui.screens.episodedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesimpsons.domain.data_classes.EpisodeDomain
import com.example.thesimpsons.domain.repository.DataRepository
import com.example.thesimpsons.domain.usecases.user_preferences.GetDarkMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class EpisodeDetailsViewModel @Inject constructor(
    val repository: DataRepository,
    getDarkMode: GetDarkMode,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val episodeId = savedStateHandle.getStateFlow("episodeId", -1)

    val episodeDetails =
        episodeId.filter { it != -1 }.flatMapLatest { id -> repository.getSingleEpisode(id) }
    val darkMode =
        getDarkMode().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val uiState = combine(episodeDetails, darkMode) { details, darkMode ->
        EpisodeUiState.Success(details, darkMode) as EpisodeUiState
    }
        .onStart { emit(EpisodeUiState.Loading) }
        .catch { emit(EpisodeUiState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EpisodeUiState.Loading)

    fun setEpisodeId(id: Int) {
        savedStateHandle["episodeId"] = id
    }

}

sealed class EpisodeUiState() {
    data object Loading : EpisodeUiState()
    data class Success(val episode: EpisodeDomain, val darkMode: Boolean) : EpisodeUiState()
    data class Error(val error: Throwable) : EpisodeUiState()
}