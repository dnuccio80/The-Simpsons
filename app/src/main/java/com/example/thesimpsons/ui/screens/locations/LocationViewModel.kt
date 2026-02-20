package com.example.thesimpsons.ui.screens.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.thesimpsons.data.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(repository: RepositoryImpl):ViewModel(){

    val locations = repository.getAllLocations().cachedIn(viewModelScope)

    private val _query = MutableStateFlow("")
    val query:StateFlow<String> = _query

    fun updateQuery(newValue:String) {
        _query.value = newValue
    }

}