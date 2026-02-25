package com.example.thesimpsons.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.thesimpsons.domain.data_classes.CharacterDomain
import com.example.thesimpsons.domain.usecases.data.GetCharacters
import com.example.thesimpsons.domain.usecases.user_preferences.GetDarkMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacters: GetCharacters,
    getDarkMode: GetDarkMode,
) :
    ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val darkMode = getDarkMode().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val characters: Flow<PagingData<CharacterDomain>> = query
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query -> getCharacters(query) }
        .cachedIn(viewModelScope)

    fun updateQuery(newValue: String) {
        _query.value = newValue
    }

}