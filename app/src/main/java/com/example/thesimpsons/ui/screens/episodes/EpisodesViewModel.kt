package com.example.thesimpsons.ui.screens.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.thesimpsons.data.RepositoryImpl
import com.example.thesimpsons.domain.usecases.GetEpisodes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(getEpisodes: GetEpisodes):ViewModel() {

    private val _query = MutableStateFlow("")
    val query:StateFlow<String> = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val episodeList = query
        .debounce(300)
        .flatMapLatest { query ->  getEpisodes(query)}
        .cachedIn(viewModelScope)

    fun updateQuery(newValue:String) {
        _query.value = newValue
    }

}