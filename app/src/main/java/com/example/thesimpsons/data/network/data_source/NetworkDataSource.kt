package com.example.thesimpsons.data.network.data_source

import com.example.thesimpsons.data.network.ApiService
import com.example.thesimpsons.data.network.dto.CharacterDto
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getSingleCharacter(id: Int): CharacterDto = apiService.getSingleCharacter(id)

}