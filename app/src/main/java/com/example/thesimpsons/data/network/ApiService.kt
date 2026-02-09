package com.example.thesimpsons.data.network

import com.example.thesimpsons.data.network.dto.CharacterDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiService @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getSingleCharacter(id:Int):CharacterDto = apiClient.getSingleCharacter("characters/$id")

}