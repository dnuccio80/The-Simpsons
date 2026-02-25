package com.example.thesimpsons.domain.usecases.data

import com.example.thesimpsons.domain.data_classes.CharacterDomain
import com.example.thesimpsons.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSingleCharacter @Inject constructor(private val repository: DataRepository) {

    suspend operator fun invoke(id: Int): Flow<CharacterDomain> = repository.getSingleCharacter(id)

}