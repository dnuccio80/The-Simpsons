package com.example.thesimpsons.data.network.dto.episode

data class EpisodeWrapper(
    val next:String?,
    val prev:String?,
    val results:List<EpisodeDto>
)
