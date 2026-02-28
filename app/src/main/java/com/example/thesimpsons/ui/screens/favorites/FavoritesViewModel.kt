package com.example.thesimpsons.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.thesimpsons.domain.data_classes.CharacterDomain
import com.example.thesimpsons.domain.data_classes.EpisodeDomain
import com.example.thesimpsons.domain.data_classes.LocationDomain
import com.example.thesimpsons.domain.usecases.data.GetCharacters
import com.example.thesimpsons.domain.usecases.data.GetEpisodes
import com.example.thesimpsons.domain.usecases.data.GetLocations
import com.example.thesimpsons.domain.usecases.user_preferences.GetDarkMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getCharacters: GetCharacters,
    getEpisodes: GetEpisodes,
    getLocations: GetLocations,
    getDarkMode: GetDarkMode,
) : ViewModel() {

    val characters: Flow<PagingData<CharacterDomain>> = getCharacters("").cachedIn(viewModelScope)

    val darkMode = getDarkMode().stateIn(viewModelScope, SharingStarted.Eagerly, false)



}

sealed class FavoritesUiState {

    data object Loading : FavoritesUiState()
    data class Success(
        val characters: PagingData<CharacterDomain>? = null,
        val episodes: PagingData<EpisodeDomain>? = null,
        val locations: PagingData<LocationDomain>? = null,
    ) : FavoritesUiState()
    data class Error(val e: Throwable): FavoritesUiState()
}