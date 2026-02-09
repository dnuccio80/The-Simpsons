package com.example.thesimpsons

import com.example.thesimpsons.data.network.data_source.NetworkDataSource
import com.example.thesimpsons.domain.CharacterDomain
import com.example.thesimpsons.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val network:NetworkDataSource) :Repository {

    override suspend fun getSingleCharacter(id: Int): CharacterDomain {
        return network.getSingleCharacter(id).toDomain()
    }
}