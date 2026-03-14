package com.danucdev.thesimpsons.domain.usecases.user_preferences

import com.danucdev.thesimpsons.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFirstAccess @Inject constructor(private val repository: UserPreferencesRepository) {
    operator fun invoke(): Flow<Boolean> = repository.isFirstAccess
}