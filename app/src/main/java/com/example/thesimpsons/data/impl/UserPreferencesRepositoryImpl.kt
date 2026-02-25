package com.example.thesimpsons.data.impl

import com.example.thesimpsons.data.local.data_store.PreferencesDataSource
import com.example.thesimpsons.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(private val dataSource:PreferencesDataSource):UserPreferencesRepository {

    override suspend fun setUsername(username: String) {
        dataSource.setUsername(username)
    }

    override val username: Flow<String> = dataSource.userNameFlow

    override val isFirstAccess: Flow<Boolean>
        get() = dataSource.firstAccess

    override suspend fun setFirstAccess(firstAccess: Boolean) {
        dataSource.setFirstAccess(firstAccess)
    }

    override val darkMode: Flow<Boolean>
        get() = dataSource.darkMode

    override suspend fun setDarkMode(enabled: Boolean) {
        dataSource.setDarkMode(enabled)
    }
}