package com.example.thesimpsons.domain

interface Repository {

    suspend fun getSingleCharacter(id:Int):CharacterDomain

}