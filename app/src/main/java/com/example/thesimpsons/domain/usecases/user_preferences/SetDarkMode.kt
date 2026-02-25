package com.example.thesimpsons.domain.usecases.user_preferences

import com.example.thesimpsons.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SetDarkMode @Inject constructor(private val repository: UserPreferencesRepository) {
    suspend operator fun invoke(enabled:Boolean) = repository.setDarkMode(enabled)
}