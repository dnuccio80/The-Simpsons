package com.example.thesimpsons.domain

import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getSingleCharacter(id:Int):Flow<CharacterDomain>

}