package com.example.thesimpsons.data

import com.example.thesimpsons.data.local.LocalDataSource
import com.example.thesimpsons.data.network.data_source.NetworkDataSource
import com.example.thesimpsons.domain.CharacterDomain
import com.example.thesimpsons.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val network:NetworkDataSource, private val local:LocalDataSource) :Repository {

    override suspend fun getSingleCharacter(id: Int): Flow<CharacterDomain> {
        return local.getSingleCharacter(id)
            .onStart {
                val exists = local.characterExists(id)
                if(!exists) local.addCharacter(network.getSingleCharacter(id).toEntity())
            }
            .filterNotNull()
            .map { it.toDomain() }
    }
}