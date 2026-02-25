package com.example.thesimpsons.ui.screens.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is MainUiState.Error -> {
            Text("Error: ${(uiState as MainUiState.Error).error}")
        }

        MainUiState.Loading -> {
            Box(
                Modifier.fillMaxSize().background(Color.Black),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }

        is MainUiState.Success -> {
            SuccessScreen((uiState as MainUiState.Success).firstAccess)
        }
    }
}

@Composable
fun SuccessScreen(firstAccess: Boolean) {

    val bottomBarDestinations = listOf(
        Characters,
        Episodes,
        Location
    )

    val mainNavKey = if (firstAccess) OnBoarding else Characters

    val backStack = rememberNavBackStack(mainNavKey)
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
                entry<OnBoarding> {
                    OnBoardingScreen(innerPadding) {
                        backStack.clearAndNavigateTo(
                            Characters
                        )
                    }
                }
                entry<Characters> {
                    CharactersScreen(innerPadding) {
                        backStack.navigateTo(
                            CharacterDetails(it)
                        )
                    }
                }
                entry<CharacterDetails> { key ->
                    CharacterDetailsScreen(
                        key.id,
                        innerPadding
                    ) { backStack.back() }
                }
                entry<Episodes> {
                    EpisodesScreen(innerPadding) {
                        backStack.navigateTo(
                            EpisodeDetails(
                                it
                            )
                        )
                    }
                }
                entry<EpisodeDetails> { key ->
                    EpisodeDetailsScreen(
                        key.id,
                        innerPadding
                    ) { backStack.back() }
                }
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
