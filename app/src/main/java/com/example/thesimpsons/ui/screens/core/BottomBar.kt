package com.example.thesimpsons.ui.screens.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun BottomBar(onNavigate:() -> Unit) {

    val navBarItems = listOf(
        NavigationItem.Characters,
        NavigationItem.Episodes,
        NavigationItem.Locations
    )

    var idSelected by rememberSaveable { mutableIntStateOf(1) }

    NavigationBar(modifier = Modifier.fillMaxWidth(), contentColor = Color.Red) {
        navBarItems.forEach {
            NavigationBarItem(
                selected = idSelected == it.id,
                onClick = {
                    idSelected = it.id
                    onNavigate()
                },
                icon = it.icon,
                label = { Text(it.name) }
            )
        }
    }

}