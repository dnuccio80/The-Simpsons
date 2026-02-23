package com.example.thesimpsons.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes(val route:String) {
    data object OnBoarding:Routes("onBoarding")
    data object Characters:Routes("characters")
    data object CharacterDetails:Routes("characterDetails/{id}") {
        fun createRoute(id:Int) = "characterDetails/$id"
    }
    data object Episodes:Routes("episodes")
    data object EpisodeDetails:Routes("episodeDetails/{id}") {
        fun createRoute(id:Int) = "episodeDetails/$id"
    }
    data object Location:Routes("location")
}

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
}