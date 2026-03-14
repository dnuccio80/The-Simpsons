package com.danucdev.thesimpsons.data.network

import com.danucdev.thesimpsons.data.network.dto.character.CharacterDto
import com.danucdev.thesimpsons.data.network.dto.character.CharacterWrapper
import com.danucdev.thesimpsons.data.network.dto.episode.EpisodeDto
import com.danucdev.thesimpsons.data.network.dto.episode.EpisodeWrapper
import com.danucdev.thesimpsons.data.network.dto.locations.LocationWrapper
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiClient {

    @GET
    suspend fun getSingleCharacter(@Url url: String): CharacterDto

    @GET("characters")
    suspend fun getAllCharacters(@Query("page") page: Int): CharacterWrapper

    @GET
    suspend fun getSingleEpisode(@Url url: String): EpisodeDto

    @GET("episodes")
    suspend fun getAllEpisodes(@Query("page") page: Int): EpisodeWrapper

    @GET("locations")
    suspend fun getAllLocations(@Query("page") page: Int): LocationWrapper


}