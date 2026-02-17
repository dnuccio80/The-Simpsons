package com.example.thesimpsons.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thesimpsons.domain.EpisodeDomain

@Entity
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val airDate: String,
    val episodeNumber: Int,
    val imagePath: String,
    val name: String,
    val season: Int,
    val page:Int,
    val synopsis: String,
) {
    fun toDomain():EpisodeDomain = EpisodeDomain(
        id = id,
        airDate = airDate,
        episodeNumber = episodeNumber,
        imagePath = imagePath,
        name = name,
        season = season,
        page = page,
        synopsis = synopsis
    )
}
