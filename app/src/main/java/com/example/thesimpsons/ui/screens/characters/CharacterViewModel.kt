package com.example.thesimpsons.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.thesimpsons.data.RepositoryImpl
import com.example.thesimpsons.domain.CharacterDomain
import com.example.thesimpsons.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(repository: RepositoryImpl):ViewModel() {

    val characters: Flow<PagingData<CharacterDomain>> = repository.getAllCharacters().cachedIn(viewModelScope)

}