package com.example.thesimpsons.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.thesimpsons.R

sealed class NavigationItem(val route:String, val name:String,val icon:@Composable () -> Unit) {

    data object Characters: NavigationItem(route = "characters", name = "Characters", { Icon(
        painterResource(R.drawable.homer_ic),
        contentDescription = "",
        modifier = Modifier.size(24.dp)
    ) })

    data object Episodes: NavigationItem(route = "episodes", name = "Episodes", { Icon(
        painterResource(R.drawable.camera_ic),
        contentDescription = "",
        modifier = Modifier.size(24.dp)
    ) })

    data object Locations: NavigationItem(route = "locations" , name = "Locations", { Icon(
        Icons.Filled.LocationOn,
        contentDescription = "",
        modifier = Modifier.size(24.dp)
    ) })


}
