package com.example.thesimpsons.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class NavRoutes : NavKey {
    @Serializable
    data object OnBoarding:NavRoutes()
    @Serializable
    data object Characters:NavRoutes()
    @Serializable
    data class CharacterDetails(val id:Int):NavRoutes()
    @Serializable
    data object Episodes:NavRoutes()
    @Serializable
    data class EpisodeDetails(val id:Int):NavRoutes()
    @Serializable
    data object Location:NavRoutes()
    @Serializable
    data object Profile: NavRoutes()
    @Serializable
    data object Favorites: NavRoutes()
}