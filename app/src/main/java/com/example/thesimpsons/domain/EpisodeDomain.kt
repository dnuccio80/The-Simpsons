package com.example.thesimpsons.domain

data class EpisodeDomain(
    val id: Int,
    val airDate: String,
    val episodeNumber: Int,
    val imagePath: String,
    val name: String,
    val season: Int,
    val page:Int,
    val synopsis: String,
)
