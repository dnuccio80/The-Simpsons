package com.example.thesimpsons.data.network.dto.episode

import com.example.thesimpsons.data.local.entity.EpisodeEntity
import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    val id: Int,
    @SerializedName("airdate") val airDate: String?,
    @SerializedName("episode_number") val episodeNumber: Int?,
    @SerializedName("image_path") val imagePath: String?,
    val name: String?,
    val season: Int?,
    val synopsis: String?,
) {
    fun toEntity():EpisodeEntity = EpisodeEntity(
        id = id,
        airDate = airDate.orEmpty(),
        episodeNumber = episodeNumber?:0,
        imagePath = imagePath.orEmpty(),
        name = name.orEmpty(),
        season = season?:0,
        page = 0,
        synopsis = synopsis.orEmpty(),
    )
}