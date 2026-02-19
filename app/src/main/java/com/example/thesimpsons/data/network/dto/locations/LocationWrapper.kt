package com.example.thesimpsons.data.network.dto.locations

data class LocationWrapper(
    val next:String?,
    val prev:String?,
    val results:List<LocationDto>
)
