package com.example.thesimpsons.data.network.dto.character

data class CharacterWrapper(
    val next:String?,
    val prev:String?,
    val results:List<CharacterDto>
)
