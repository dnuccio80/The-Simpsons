package com.example.thesimpsons.ui.screens.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.thesimpsons.R

@Composable
fun BottomBar() {

    val iconColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    NavigationBar(modifier = Modifier.fillMaxWidth(), contentColor = Color.Red) {

        NavigationBarItem(
            selected = false,
            onClick = {},
            label = { Text("Characters") },
            icon = {
                Icon(
                    painterResource(R.drawable.homer_ic),
                    contentDescription = "",
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            label = { Text("Episodes") },
            icon = {
                Icon(
                    painterResource(R.drawable.camera_ic),
                    contentDescription = "",
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            label = { Text("Locations") },
            icon = {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = "",
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        )
    }

}