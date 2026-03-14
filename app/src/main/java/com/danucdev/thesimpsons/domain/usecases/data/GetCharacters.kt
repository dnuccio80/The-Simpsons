package com.danucdev.thesimpsons.domain.usecases.data

import androidx.paging.PagingData
import com.danucdev.thesimpsons.domain.data_classes.CharacterDomain
import com.danucdev.thesimpsons.domain.repository.DataRepository
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