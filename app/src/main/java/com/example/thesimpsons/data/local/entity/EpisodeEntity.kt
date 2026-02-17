package com.example.thesimpsons.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thesimpsons.domain.EpisodeDomain

@Entity
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val airDate: String,
    val episodeNumber: String,
    val imagePath: String,
    val name: String,
    val season: String,
    val page:Int,
    val synopsis: String,
) {
    fun toDomain():EpisodeDomain = EpisodeDomain(
        id = id,
        airDate = airDate.ifBlank { "Unknown" },
        episodeNumber = episodeNumber.ifBlank { "Unknown" },
        imagePath = imagePath,
        name = name,
        season = season.ifBlank { "Unknown" },
        page = page,
        synopsis = synopsis
    )
}
