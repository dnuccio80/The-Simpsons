package com.example.thesimpsons.ui.screens.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.thesimpsons.ui.core.BottomBar
import com.example.thesimpsons.ui.core.TitleItem
import com.example.thesimpsons.ui.core.extensions.back
import com.example.thesimpsons.ui.core.extensions.backTo
import com.example.thesimpsons.ui.core.extensions.clearAndNavigateTo
import com.example.thesimpsons.ui.core.extensions.navigateTo
import com.example.thesimpsons.ui.navigation.ModalDrawerAction
import com.example.thesimpsons.ui.screens.characterdetails.CharacterDetailsScreen
import com.example.thesimpsons.ui.screens.characters.CharactersScreen
import com.example.thesimpsons.ui.screens.episodedetails.EpisodeDetailsScreen
import com.example.thesimpsons.ui.screens.episodes.EpisodesScreen
import com.example.thesimpsons.ui.screens.locations.LocationScreen
import com.example.thesimpsons.ui.navigation.ModalDrawerItem
import com.example.thesimpsons.ui.navigation.NavRoutes
import com.example.thesimpsons.ui.navigation.NavRoutes.*
import com.example.thesimpsons.ui.screens.favorites.FavoritesScreen
import com.example.thesimpsons.ui.screens.onboarding.OnBoardingScreen
import com.example.thesimpsons.ui.screens.profile.ProfileScreen
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is MainUiState.Error -> {
            Text("Error: ${(uiState as MainUiState.Error).error}")
        }

        MainUiState.Loading -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }

        is MainUiState.Success -> {
            SuccessScreen(uiState as MainUiState.Success, onDarkModeClick = { viewModel.toggleDarkMode() })
        }
    }
}

@Composable
fun SuccessScreen(uiState: MainUiState.Success, onDarkModeClick:() -> Unit) {

    val bottomBarDestinations = listOf(
        Characters,
        Episodes,
        Location
    )
    val mainNavKey = if (uiState.firstAccess) OnBoarding else Characters

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val backStack = rememberNavBackStack(mainNavKey)
    val showBottomBar = backStack.last() in bottomBarDestinations
    val currentRoute = backStack.last()

    ModalDrawerItem(
        drawerState,
        onActionDone =  { action ->
            when(action) {
                ModalDrawerAction.TOGGLE_DARK_MODE -> { onDarkModeClick() }
                ModalDrawerAction.PROFILE_CLICK -> {
                    scope.launch { drawerState.close() }
                    backStack.navigateTo(Profile)
                }
                ModalDrawerAction.FAVORITE_CLICK -> {
                    scope.launch { drawerState.close() }
                    backStack.navigateTo(Favorites)
                }
            }
        },
        darkMode = uiState.darkMode,
        username = uiState.username,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (showBottomBar) BottomBar(
                    currentRoute = currentRoute
                )
                {
                    backStack.backTo(it)
                }
            },
            topBar = { TopBarItem { scope.launch { drawerState.open() } } }

        ) { innerPadding ->

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
                    entry<Profile> { ProfileScreen(innerPadding) { backStack.back() } }
                    entry<Favorites> { FavoritesScreen(innerPadding) }
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
}

@Composable
fun TopBarItem(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 36.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(Icons.Default.Menu, "menu icon", modifier = Modifier.padding(8.dp).clickable { onMenuClick() })
        TitleItem("The Simpsons API")
        Spacer(Modifier.size(12.dp))
    }
}
