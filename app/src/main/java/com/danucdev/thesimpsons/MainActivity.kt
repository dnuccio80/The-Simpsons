package com.danucdev.thesimpsons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.danucdev.thesimpsons.ui.screens.main.MainScreen
import com.danucdev.thesimpsons.ui.theme.TheSimpsonsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheSimpsonsTheme {
                MainScreen()
            }
        }
    }
}


