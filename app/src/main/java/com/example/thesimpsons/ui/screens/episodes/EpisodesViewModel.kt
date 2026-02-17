package com.example.thesimpsons.ui.screens.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.thesimpsons.data.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(repository: RepositoryImpl):ViewModel() {

    val episodeList = repository.getAllEpisodes().cachedIn(viewModelScope)

}