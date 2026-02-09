package com.example.thesimpsons.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thesimpsons.domain.CharacterDomain

@Entity
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val age: Int,
    val birthdate: String,
    val gender: String,
    val name: String,
    val occupation: String,
    val portraitImage: String,
    val phrases: List<String>,
    val status: String,
) {
    fun toDomain():CharacterDomain = CharacterDomain(
        id = id,
        age = age,
        birthdate = birthdate,
        gender = gender,
        name = name,
        occupation = occupation,
        portraitImage = portraitImage,
        phrases = phrases,
        status = status
    )
}
