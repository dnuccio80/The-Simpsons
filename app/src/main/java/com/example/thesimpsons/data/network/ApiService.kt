package com.example.thesimpsons.data.network

import androidx.paging.PagingData
import com.example.thesimpsons.data.network.dto.CharacterDto
import com.example.thesimpsons.data.network.dto.CharacterWrapper
import com.example.thesimpsons.domain.CharacterDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiService @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getSingleCharacter(id:Int):CharacterDto = apiClient.getSingleCharacter("characters/$id")

    suspend fun getAllCharacters(page:Int):CharacterWrapper = apiClient.getAllCharacters(page)
}