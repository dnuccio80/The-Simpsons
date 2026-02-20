package com.example.thesimpsons.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getSingleCharacter(id:Int):Flow<CharacterDomain>

    fun getAllCharacters():Flow<PagingData<CharacterDomain>>

    fun getAllCharactersByQuery(query:String):Flow<PagingData<CharacterDomain>>

    suspend fun getSingleEpisode(id:Int):Flow<EpisodeDomain>

    fun getAllEpisodes():Flow<PagingData<EpisodeDomain>>

    fun getEpisodesByQuery(query:String):Flow<PagingData<EpisodeDomain>>

    fun getAllLocations():Flow<PagingData<LocationDomain>>

    fun getLocationsByQuery(query:String):Flow<PagingData<LocationDomain>>

}