package com.example.thesimpsons.ui.screens.modaldrawer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

@Composable
fun ModalDrawerItem(
    drawerState: DrawerState,
    viewModel: ModalDrawerViewModel = hiltViewModel(),
    onProfileClick:() -> Unit,
    content: @Composable () -> Unit,
) {

    val darkMode by viewModel.darkMode.collectAsStateWithLifecycle()
    val username by viewModel.username.collectAsStateWithLifecycle()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.size(24.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SubtitleItem(
                        "Welcome $username",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                    )
                    Icon(painterResource(R.drawable.ic_sun), "")
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
                NavigationDrawerItem(
                    label = { Text("Profile") },
                    selected = false,
                    onClick = { onProfileClick() },
                    icon = { Icon(Icons.Default.Person, "profile icon") },
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
                    onClick = { viewModel.modifyDarkMode(!darkMode) },
                    icon = { Icon(painterResource(R.drawable.ic_dark_mode), "share icon") },
                    shape = RoundedCornerShape(4.dp),
                    badge = {
                        Switch(
                            checked = darkMode,
                            onCheckedChange = { viewModel.modifyDarkMode(!darkMode) })
                    }
                )
            }
        }
    ) {
        content()
    }

}