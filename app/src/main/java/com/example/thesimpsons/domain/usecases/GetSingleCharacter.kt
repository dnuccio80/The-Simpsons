package com.example.thesimpsons.domain.usecases

import com.example.thesimpsons.data.RepositoryImpl
import com.example.thesimpsons.domain.CharacterDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSingleCharacter @Inject constructor(private val repository: RepositoryImpl) {

    suspend operator fun invoke(id:Int):Flow<CharacterDomain> = repository.getSingleCharacter(id)

}