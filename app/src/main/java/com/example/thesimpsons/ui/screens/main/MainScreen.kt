package com.example.thesimpsons.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.thesimpsons.ui.core.BottomBar
import com.example.thesimpsons.ui.navigation.Routes
import com.example.thesimpsons.ui.screens.characterdetails.CharacterDetailsScreen
import com.example.thesimpsons.ui.screens.characters.CharactersScreen
import com.example.thesimpsons.ui.screens.episodedetails.EpisodeDetailsScreen
import com.example.thesimpsons.ui.screens.episodes.EpisodesScreen
import com.example.thesimpsons.ui.screens.locations.LocationScreen
import com.example.thesimpsons.ui.screens.onboarding.OnBoardingScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val bottomBarDestinations = listOf(
        Routes.Characters.route,
        Routes.Episodes.route,
        Routes.Location.route,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val rootDestination = Routes.Characters.route

    val showBottomBar = currentRoute in bottomBarDestinations

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) BottomBar(
                currentRoute = currentRoute
            )
            {
                navController.navigate(it) {
                    popUpTo(rootDestination) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }) { innerPadding ->

        NavHost(navController, startDestination = Routes.OnBoarding.route) {
            composable(Routes.OnBoarding.route) { OnBoardingScreen(innerPadding) {
                navController.navigate(Routes.Characters.route) {
                    popUpTo(Routes.OnBoarding.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            } }
            composable(Routes.Characters.route) {
                CharactersScreen(innerPadding) {
                    navController.navigate(Routes.CharacterDetails.createRoute(it)) {
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            }
            composable(
                Routes.CharacterDetails.route, arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )) { navBackStackEntry ->
                CharacterDetailsScreen(
                    innerPadding,
                    navBackStackEntry.arguments?.getInt("id") ?: 0
                ) {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            }
            composable(Routes.Episodes.route) {
                EpisodesScreen(innerPadding) {
                    navController.navigate(
                        Routes.EpisodeDetails.createRoute(it)
                    )
                }
            }
            composable(
                Routes.EpisodeDetails.route, arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )) { navBackStackEntry ->
                EpisodeDetailsScreen(
                    id = navBackStackEntry.arguments?.getInt("id") ?: 0,
                    innerPadding = innerPadding
                ) {
                    if (navController.previousBackStackEntry != null) navController.popBackStack()
                }
            }
            composable(Routes.Location.route) { LocationScreen(innerPadding) }
        }
    }
}