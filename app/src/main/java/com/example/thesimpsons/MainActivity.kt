package com.example.thesimpsons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import com.example.thesimpsons.ui.screens.modaldrawer.ModalDrawerItem
import com.example.thesimpsons.ui.screens.main.MainScreen
import com.example.thesimpsons.ui.theme.TheSimpsonsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheSimpsonsTheme {

                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalDrawerItem(
                    drawerState,
                    onProfileClick = {
                        scope.launch { drawerState.close() }
                    },
                    content = {
                        MainScreen { scope.launch { drawerState.open() } }
                    }
                )
            }
        }
    }
}


