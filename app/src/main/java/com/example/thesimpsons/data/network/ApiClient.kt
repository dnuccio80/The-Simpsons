package com.example.thesimpsons.data.network

import com.example.thesimpsons.data.network.dto.CharacterDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiClient {

    @GET
    suspend fun getSingleCharacter(@Url url:String):CharacterDto

}