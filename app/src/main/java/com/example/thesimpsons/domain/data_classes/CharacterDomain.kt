package com.example.thesimpsons.domain.data_classes

data class CharacterDomain(
    val id: Int,
    val age: Int,
    val birthdate: String,
    val gender: String,
    val name: String,
    val occupation: String,
    val portraitImage: String,
    val phrases: List<String>,
    val isAlive: Boolean,
)