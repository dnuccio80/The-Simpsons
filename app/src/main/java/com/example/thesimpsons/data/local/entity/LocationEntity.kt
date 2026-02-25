package com.example.thesimpsons.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thesimpsons.domain.data_classes.LocationDomain

@Entity
data class LocationEntity (
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val name:String,
    val imagePath:String,
    val town:String,
    val page:Int,
    val use:String
) {
    fun toDomain(): LocationDomain = LocationDomain(
        id = id,
        name = name.ifBlank { "Unknown" },
        imagePath = imagePath.ifBlank { "" },
        town = town.ifBlank { "Unknown" },
        page = page,
        use = use.ifBlank { "Unknown" }
    )
}