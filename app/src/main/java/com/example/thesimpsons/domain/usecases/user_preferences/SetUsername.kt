package com.example.thesimpsons.domain.usecases.user_preferences

import com.example.thesimpsons.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SetUsername @Inject constructor(private val repository: UserPreferencesRepository) {

    suspend operator fun invoke(username: String) = repository.setUsername(username)

}