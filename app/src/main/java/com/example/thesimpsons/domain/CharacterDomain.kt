package com.example.thesimpsons.domain

import com.google.gson.annotations.SerializedName

data class CharacterDomain(
    val id: Int,
    val age: Int,
    val birthdate: String,
    val gender: String,
    val name: String,
    val occupation: String,
    val portraitImage: String,
    val phrases: List<String>,
    val status: String,
)