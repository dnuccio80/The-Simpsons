package com.example.thesimpsons.ui.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavKey
import com.example.thesimpsons.ui.navigation.NavigationItem

@Composable
fun BottomBar(
    currentRoute:NavKey,
    onClick:(NavKey) -> Unit,
) {

    val navBarItems = listOf(
        NavigationItem.Characters,
        NavigationItem.Episodes,
        NavigationItem.Locations
    )

    NavigationBar(modifier = Modifier.fillMaxWidth(), contentColor = Color.Red) {
        navBarItems.forEach {
            NavigationBarItem(
                selected = currentRoute == it.route,
                onClick = { onClick(it.route) },
                icon = it.icon,
                label = { Text(it.name) }
            )
        }
    }

}