package com.example.thesimpsons.data.network

import com.example.thesimpsons.data.network.dto.character.CharacterDto
import com.example.thesimpsons.data.network.dto.character.CharacterWrapper
import com.example.thesimpsons.data.network.dto.episode.EpisodeDto
import com.example.thesimpsons.data.network.dto.episode.EpisodeWrapper
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiClient {

    @GET
    suspend fun getSingleCharacter(@Url url:String): CharacterDto

    @GET("characters")
    suspend fun getAllCharacters(@Query("page") page:Int): CharacterWrapper

    @GET
    suspend fun getSingleEpisode(@Url url:String): EpisodeDto

    @GET("episodes")
    suspend fun getAllEpisodes(@Query("page") page: Int): EpisodeWrapper


}