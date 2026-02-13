package com.example.thesimpsons.ui.navigation

sealed class Routes(val route:String) {
    data object Characters:Routes("characters")
    data object CharacterDetails:Routes("characterDetails/{id}") {
        fun createRoute(id:Int) = "characterDetails/$id"
    }
}
