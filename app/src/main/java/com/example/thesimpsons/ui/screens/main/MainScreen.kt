package com.example.thesimpsons.ui.screens.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.thesimpsons.ui.core.BottomBar
import com.example.thesimpsons.ui.core.extensions.back
import com.example.thesimpsons.ui.core.extensions.backTo
import com.example.thesimpsons.ui.core.extensions.navigateTo
import com.example.thesimpsons.ui.navigation.NavRoutes.*
import com.example.thesimpsons.ui.screens.characterdetails.CharacterDetailsScreen
import com.example.thesimpsons.ui.screens.characters.CharactersScreen
import com.example.thesimpsons.ui.screens.episodedetails.EpisodeDetailsScreen
import com.example.thesimpsons.ui.screens.episodes.EpisodesScreen
import com.example.thesimpsons.ui.screens.locations.LocationScreen
import com.example.thesimpsons.ui.screens.onboarding.OnBoardingScreen

@Composable
fun MainScreen() {

    val bottomBarDestinations = listOf(
        Characters,
        Episodes,
        Location
    )

    val backStack = rememberNavBackStack(Characters)
    val showBottomBar = backStack.last() in bottomBarDestinations
    val currentRoute = backStack.last()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) BottomBar(
                currentRoute = currentRoute
            )
            {
                backStack.backTo(it)
            }
        }) { innerPadding ->

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.back() },
            entryProvider = entryProvider {
                entry<OnBoarding> { OnBoardingScreen(innerPadding) { } }
                entry<Characters> { CharactersScreen(innerPadding) { backStack.navigateTo(CharacterDetails(it)) } }
                entry<CharacterDetails> { key -> CharacterDetailsScreen(key.id, innerPadding) { backStack.back() } }
                entry<Episodes> { EpisodesScreen(innerPadding) { backStack.navigateTo(EpisodeDetails(it)) } }
                entry<EpisodeDetails> { key -> EpisodeDetailsScreen(key.id, innerPadding) { backStack.back() } }
                entry<Location> { LocationScreen(innerPadding) }
            },
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            popTransitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                )
            }
        )

//        NavHost(navController, startDestination = Routes.OnBoarding.route) {
//            composable(Routes.OnBoarding.route) { OnBoardingScreen(innerPadding) {
//                navController.navigate(Routes.Characters.route) {
//                    popUpTo(Routes.OnBoarding.route) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
//            } }
//            composable(Routes.Characters.route) {
//                CharactersScreen(innerPadding) {
//                    navController.navigate(Routes.CharacterDetails.createRoute(it)) {
//                        restoreState = true
//                        launchSingleTop = true
//                    }
//                }
//            }
//            composable(
//                Routes.CharacterDetails.route, arguments = listOf(
//                    navArgument("id") {
//                        type = NavType.IntType
//                    }
//                )) { navBackStackEntry ->
//                CharacterDetailsScreen(
//                    innerPadding,
//                    navBackStackEntry.arguments?.getInt("id") ?: 0
//                ) {
//                    if (navController.previousBackStackEntry != null) {
//                        navController.popBackStack()
//                    }
//                }
//            }
//            composable(Routes.Episodes.route) {
//                EpisodesScreen(innerPadding) {
//                    navController.navigate(
//                        Routes.EpisodeDetails.createRoute(it)
//                    )
//                }
//            }
//            composable(
//                Routes.EpisodeDetails.route, arguments = listOf(
//                    navArgument("id") {
//                        type = NavType.IntType
//                    }
//                )) { navBackStackEntry ->
//                EpisodeDetailsScreen(
//                    id = navBackStackEntry.arguments?.getInt("id") ?: 0,
//                    innerPadding = innerPadding
//                ) {
//                    if (navController.previousBackStackEntry != null) navController.popBackStack()
//                }
//            }
//            composable(Routes.Location.route) { LocationScreen(innerPadding) }
//        }
    }
}