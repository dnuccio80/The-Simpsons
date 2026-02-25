package com.example.thesimpsons.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    // Username
    val username: Flow<String>
    suspend fun setUsername(username:String)

    val isFirstAccess: Flow<Boolean>
    suspend fun setFirstAccess(firstAccess:Boolean)

    val darkMode:Flow<Boolean>
    suspend fun setDarkMode(enabled:Boolean)

}