package com.example.thesimpsons.ui.screens.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.thesimpsons.data.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(repository: RepositoryImpl):ViewModel(){

    val locations = repository.getAllLocations().cachedIn(viewModelScope)

}