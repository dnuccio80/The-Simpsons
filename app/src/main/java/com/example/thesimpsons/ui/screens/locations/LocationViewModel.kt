package com.example.thesimpsons.ui.screens.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.thesimpsons.domain.usecases.data.GetLocations
import com.example.thesimpsons.domain.usecases.user_preferences.GetDarkMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val getLocations: GetLocations, getDarkMode: GetDarkMode) : ViewModel() {


    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    val darkMode = getDarkMode().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    @OptIn(ExperimentalCoroutinesApi::class,FlowPreview::class)
    val locations = query
        .debounce(300)
        .flatMapLatest { query ->
            getLocations(query)
        }
        .cachedIn(viewModelScope)

    fun updateQuery(newValue: String) {
        _query.value = newValue
    }

}