package com.example.thesimpsons.data.network.dto

import com.example.thesimpsons.data.local.entity.CharacterEntity
import com.example.thesimpsons.domain.CharacterDomain
import com.google.gson.annotations.SerializedName

data class CharacterDto(
    val id: Int,
    val age: Int?,
    val birthdate: String?,
    val gender: String?,
    val name: String?,
    val occupation: String?,
    @SerializedName("portrait_path") val portraitImage: String?,
    val phrases: List<String>?,
    val status: String?,
) {
    fun toEntity():CharacterEntity = CharacterEntity(
        id = id,
        age = age?:0,
        birthdate = birthdate?:"Unknown",
        gender = gender?:"Unknown",
        name = name?:"Unknown",
        occupation = occupation?:"Unknown",
        portraitImage = portraitImage.orEmpty(),
        phrases = phrases.orEmpty(),
        status = status?:"Unknown"
    )
}
