package com.example.thesimpsons.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesimpsons.domain.usecases.user_preferences.GetDarkMode
import com.example.thesimpsons.domain.usecases.user_preferences.SetDarkMode
import com.example.thesimpsons.domain.usecases.user_preferences.SetFirstAccess
import com.example.thesimpsons.domain.usecases.user_preferences.SetUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val setUsername: SetUsername,
    private val setFirstAccess: SetFirstAccess,
    private val setDarkMode: SetDarkMode,
    getDarkMode: GetDarkMode
) : ViewModel() {

    val darkMode = getDarkMode().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private fun setNickname(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setUsername(username)
        }
    }

    private fun enterFirstTime() {
        viewModelScope.launch(Dispatchers.IO) {
            setFirstAccess(false)
        }
    }

    fun setMode(enabled:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            setDarkMode(enabled)
        }
    }

    fun setUserPreferences(username: String) {
        setNickname(username)
        enterFirstTime()
    }
}

