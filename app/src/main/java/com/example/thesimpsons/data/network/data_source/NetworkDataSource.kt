package com.example.thesimpsons.data.network.data_source

import com.example.thesimpsons.data.network.ApiService
import com.example.thesimpsons.data.network.dto.character.CharacterDto
import com.example.thesimpsons.data.network.dto.episode.EpisodeDto
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getSingleCharacter(id: Int): CharacterDto = apiService.getSingleCharacter(id)

    suspend fun getSingleEpisode(id:Int): EpisodeDto = apiService.getSingleEpisode(id)

}