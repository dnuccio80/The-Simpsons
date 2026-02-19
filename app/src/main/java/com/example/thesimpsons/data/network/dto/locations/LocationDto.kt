package com.example.thesimpsons.data.network.dto.locations

import com.example.thesimpsons.data.local.entity.LocationEntity
import com.google.gson.annotations.SerializedName

data class LocationDto(
    val id:Int,
    val name:String?,
    @SerializedName("image_path") val imagePath:String?,
    val town:String?,
    val use:String?
) {
    fun toEntity():LocationEntity = LocationEntity(
        id = id,
        name = name.orEmpty(),
        imagePath = imagePath.orEmpty(),
        town = town.orEmpty(),
        page = 0,
        use = use.orEmpty()
    )
}
