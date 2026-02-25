package com.example.thesimpsons.domain.usecases.user_preferences

import com.example.thesimpsons.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsername @Inject constructor(private val repository: UserPreferencesRepository) {

    operator fun invoke():Flow<String> = repository.username

}