package com.example.thesimpsons.ui.screens.modaldrawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesimpsons.domain.usecases.user_preferences.GetDarkMode
import com.example.thesimpsons.domain.usecases.user_preferences.GetUsername
import com.example.thesimpsons.domain.usecases.user_preferences.SetDarkMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModalDrawerViewModel @Inject constructor(getDarkMode: GetDarkMode,getUsername: GetUsername, private val setDarkMode: SetDarkMode) : ViewModel() {

    val darkMode = getDarkMode().stateIn(viewModelScope, SharingStarted.Eagerly, false)
    val username = getUsername().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    fun modifyDarkMode(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            setDarkMode(enabled)
        }
    }

}