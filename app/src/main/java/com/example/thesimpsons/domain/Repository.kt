package com.example.thesimpsons.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getSingleCharacter(id:Int):Flow<CharacterDomain>

    fun getAllCharacters():Flow<PagingData<CharacterDomain>>

}