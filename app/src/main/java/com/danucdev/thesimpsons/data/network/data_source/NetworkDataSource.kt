package com.danucdev.thesimpsons.data.network.data_source

import com.danucdev.thesimpsons.data.network.ApiService
import com.danucdev.thesimpsons.data.network.dto.character.CharacterDto
import com.danucdev.thesimpsons.data.network.dto.episode.EpisodeDto
import com.danucdev.thesimpsons.data.network.dto.locations.LocationWrapper
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getSingleCharacter(id: Int): CharacterDto = apiService.getSingleCharacter(id)

    suspend fun getSingleEpisode(id:Int): EpisodeDto = apiService.getSingleEpisode(id)

    suspend fun getAllLocations(page:Int):LocationWrapper = apiService.getAllLocations(page)

}