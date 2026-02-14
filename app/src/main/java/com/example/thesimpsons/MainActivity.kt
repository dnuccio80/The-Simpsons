package com.example.thesimpsons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.savedState
import com.example.thesimpsons.ui.navigation.Routes
import com.example.thesimpsons.ui.screens.characterdetails.CharacterDetailsScreen
import com.example.thesimpsons.ui.screens.characters.CharactersScreen
import com.example.thesimpsons.ui.screens.home.HomeScreen
import com.example.thesimpsons.ui.theme.TheSimpsonsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheSimpsonsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(navController, startDestination = Routes.Characters.route) {
                        composable(Routes.Characters.route) { CharactersScreen(innerPadding) { navController.navigate(Routes.CharacterDetails.createRoute(it)) } }
                        composable(Routes.CharacterDetails.route, arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )) {navBackStackEntry ->
                            CharacterDetailsScreen(innerPadding,navBackStackEntry.arguments?.getInt("id")?:0) {
                                navController.navigate(Routes.Characters.route) {
                                    restoreState = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

