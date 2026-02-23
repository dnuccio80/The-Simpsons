package com.example.thesimpsons.ui.screens.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.thesimpsons.ui.core.BottomBar
import com.example.thesimpsons.ui.core.extensions.back
import com.example.thesimpsons.ui.core.extensions.backTo
import com.example.thesimpsons.ui.core.extensions.clearAndNavigateTo
import com.example.thesimpsons.ui.core.extensions.navigateTo
import com.example.thesimpsons.ui.navigation.NavRoutes.CharacterDetails
import com.example.thesimpsons.ui.navigation.NavRoutes.Characters
import com.example.thesimpsons.ui.navigation.NavRoutes.EpisodeDetails
import com.example.thesimpsons.ui.navigation.NavRoutes.Episodes
import com.example.thesimpsons.ui.navigation.NavRoutes.Location
import com.example.thesimpsons.ui.navigation.NavRoutes.OnBoarding
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

        val backStack = rememberNavBackStack(OnBoarding)
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
                entry<OnBoarding> { OnBoardingScreen(innerPadding) { backStack.clearAndNavigateTo(Characters) } }
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
    }
}