package com.example.thesimpsons.data.local.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(private val dataStore:DataStore<Preferences>) {

    companion object {
        val USER_NAME = stringPreferencesKey("username")
        val FIRST_ACCESS = booleanPreferencesKey("firstAccess")
        val DARK_MODE = booleanPreferencesKey("darkMode")
    }

    val userNameFlow: Flow<String> = dataStore.data.map {preferences ->
        preferences[USER_NAME] ?: ""
    }

    val firstAccess:Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[FIRST_ACCESS] ?: true
    }

    val darkMode:Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DARK_MODE] ?: false
    }

    suspend fun setUsername(username:String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = username
        }
    }

    suspend fun setFirstAccess(firstAccess:Boolean) {
        dataStore.edit { preferences -> preferences[FIRST_ACCESS] = firstAccess }
    }

    suspend fun setDarkMode(enabled:Boolean) {
        dataStore.edit { preferences -> preferences[DARK_MODE] = enabled }
    }




}