package com.example.thesimpsons.domain.usecases.data

import androidx.paging.PagingData
import com.example.thesimpsons.domain.data_classes.CharacterDomain
import com.example.thesimpsons.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacters @Inject constructor(private val repository: DataRepository) {

    operator fun invoke(query:String): Flow<PagingData<CharacterDomain>> {
        return if(query.isBlank()) {
            repository.getAllCharacters()
        } else {
            repository.getAllCharactersByQuery(query)
        }
    }

}