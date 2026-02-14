package com.example.thesimpsons.ui.screens.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.thesimpsons.R

sealed class NavigationItem(val id:Int, val name:String,val icon:@Composable () -> Unit) {

    data object Characters:NavigationItem(id = 1, name = "Characters", { Icon(
        painterResource(R.drawable.homer_ic),
        contentDescription = "",
        modifier = Modifier.size(24.dp)
    ) })

    data object Episodes:NavigationItem(id = 2, name = "Episodes", { Icon(
        painterResource(R.drawable.camera_ic),
        contentDescription = "",
        modifier = Modifier.size(24.dp)
    ) })

    data object Locations:NavigationItem(id = 3, name = "Episodes", { Icon(
        Icons.Filled.LocationOn,
        contentDescription = "",
        modifier = Modifier.size(24.dp)
    ) })


}
