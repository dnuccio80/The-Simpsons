package com.example.thesimpsons.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.thesimpsons.R
import com.example.thesimpsons.ui.core.SubtitleItem
import com.example.thesimpsons.ui.screens.profile.ProfileUIState

@Composable
fun ModalDrawerItem(
    drawerState: DrawerState,
    darkMode:Boolean,
    username:String,
    onProfileClick:() -> Unit,
    onDarkModeClick:() -> Unit,
    content: @Composable () -> Unit,
) {

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.size(24.dp))
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.CenterStart) {
                    SubtitleItem(
                        "Welcome $username",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        singleLine = true
                    )
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
                NavigationDrawerItem(
                    label = { Text("Profile settings") },
                    selected = false,
                    onClick = { onProfileClick() },
                    icon = { Icon(Icons.Default.Person, "profile icon") },
                    shape = RoundedCornerShape(4.dp),
                )
                NavigationDrawerItem(
                    label = { Text("My favorites") },
                    selected = false,
                    onClick = { onProfileClick() },
                    icon = { Icon(Icons.Default.Favorite, "profile icon") },
                    shape = RoundedCornerShape(4.dp),
                )
                NavigationDrawerItem(
                    label = { Text("Recommend The Simpsons Api") },
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.Share, "share icon") },
                    shape = RoundedCornerShape(4.dp),
                )
                NavigationDrawerItem(
                    label = { Text("Rate us on Play Store") },
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.ThumbUp, "share icon") },
                    shape = RoundedCornerShape(4.dp),
                )
                NavigationDrawerItem(
                    label = { Text("Dark Mode") },
                    selected = false,
                    onClick = { onDarkModeClick() },
                    icon = { Icon(painterResource(R.drawable.ic_dark_mode), "share icon") },
                    shape = RoundedCornerShape(4.dp),
                    badge = {
                        Switch(
                            checked = darkMode,
                            onCheckedChange = { onDarkModeClick() })
                    }
                )
            }
        }
    ) {
        content()
    }

}