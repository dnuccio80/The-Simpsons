package com.example.thesimpsons.data.local

import com.example.thesimpsons.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao:CharacterDao) {

    suspend fun characterExists(id:Int):Boolean = dao.exists(id)

    suspend fun addCharacter(character: CharacterEntity) = dao.addCharacter(character)

    fun getSingleCharacter(id:Int): Flow<CharacterEntity?> = dao.getSingleCharacter(id)

}