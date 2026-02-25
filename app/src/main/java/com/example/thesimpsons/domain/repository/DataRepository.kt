package com.example.thesimpsons.domain.repository

import androidx.paging.PagingData
import com.example.thesimpsons.domain.data_classes.CharacterDomain
import com.example.thesimpsons.domain.data_classes.EpisodeDomain
import com.example.thesimpsons.domain.data_classes.LocationDomain
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    suspend fun getSingleCharacter(id:Int):Flow<CharacterDomain>

    fun getAllCharacters():Flow<PagingData<CharacterDomain>>

    fun getAllCharactersByQuery(query:String):Flow<PagingData<CharacterDomain>>

    suspend fun getSingleEpisode(id:Int):Flow<EpisodeDomain>

    fun getAllEpisodes():Flow<PagingData<EpisodeDomain>>

    fun getEpisodesByQuery(query:String):Flow<PagingData<EpisodeDomain>>

    fun getAllLocations():Flow<PagingData<LocationDomain>>

    fun getLocationsByQuery(query:String):Flow<PagingData<LocationDomain>>

}