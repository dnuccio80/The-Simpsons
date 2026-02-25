package com.example.thesimpsons.domain.data_classes

data class EpisodeDomain(
    val id: Int,
    val airDate: String,
    val episodeNumber: String,
    val imagePath: String,
    val name: String,
    val season: String,
    val page:Int,
    val synopsis: String,
)
