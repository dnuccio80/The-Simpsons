package com.example.thesimpsons.data.network

import com.example.thesimpsons.data.network.dto.character.CharacterDto
import com.example.thesimpsons.data.network.dto.character.CharacterWrapper
import com.example.thesimpsons.data.network.dto.episode.EpisodeDto
import com.example.thesimpsons.data.network.dto.episode.EpisodeWrapper
import com.example.thesimpsons.data.network.dto.locations.LocationWrapper
import javax.inject.Inject

class ApiService @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getSingleCharacter(id:Int): CharacterDto = apiClient.getSingleCharacter("characters/$id")

    suspend fun getAllCharacters(page:Int): CharacterWrapper = apiClient.getAllCharacters(page)

    suspend fun getSingleEpisode(id:Int): EpisodeDto = apiClient.getSingleEpisode("episodes/$id")

    suspend fun getAllEpisodes(page:Int): EpisodeWrapper = apiClient.getAllEpisodes(page)

    suspend fun getAllLocations(page:Int):LocationWrapper = apiClient.getAllLocations(page)

}