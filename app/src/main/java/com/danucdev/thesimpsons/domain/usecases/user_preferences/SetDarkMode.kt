package com.danucdev.thesimpsons.domain.usecases.user_preferences

import com.danucdev.thesimpsons.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SetDarkMode @Inject constructor(private val repository: UserPreferencesRepository) {
    suspend operator fun invoke(enabled:Boolean) = repository.setDarkMode(enabled)
}