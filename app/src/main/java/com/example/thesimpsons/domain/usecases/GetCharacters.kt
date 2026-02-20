package com.example.thesimpsons.domain.usecases

import androidx.paging.PagingData
import com.example.thesimpsons.data.RepositoryImpl
import com.example.thesimpsons.domain.CharacterDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacters @Inject constructor(private val repository: RepositoryImpl) {

    operator fun invoke(query:String): Flow<PagingData<CharacterDomain>> {
        return if(query.isBlank()) {
            repository.getAllCharacters()
        } else {
            repository.getAllCharactersByQuery(query)
        }
    }

}