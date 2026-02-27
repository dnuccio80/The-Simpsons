package com.example.thesimpsons.ui.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.thesimpsons.R
import com.example.thesimpsons.ui.core.BodyTextItem
import com.example.thesimpsons.ui.core.ScreenContainer
import com.example.thesimpsons.ui.core.SubtitleItem
import com.example.thesimpsons.ui.screens.onboarding.GenericTextField
import com.example.thesimpsons.ui.theme.YellowMain

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var query by rememberSaveable { mutableStateOf("") }
    var showError by rememberSaveable { mutableStateOf(false) }

    ScreenContainer(innerPadding) {
        when (uiState) {
            is ProfileUIState.Error -> {
                Text("Error")
            }

            ProfileUIState.Loading -> {
                CircularProgressIndicator()
            }

            is ProfileUIState.Success -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                onBack()
                            }
                        )
                    }
                    Image(
                        painterResource(R.drawable.ic_profile_photo),
                        contentDescription = "",
                        modifier = Modifier.size(250.dp),
                        contentScale = ContentScale.FillBounds
                    )
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                        SubtitleItem("Your current username: ${(uiState as ProfileUIState.Success).username}", singleLine = true)
                    }
                    SubtitleItem("Want to change it?")
                    GenericTextField(
                        query = query,
                        placeholder = "Your new username.."
                    ) { query = it }
                    AnimatedVisibility(showError) {
                        Text("You must to write your new username!", color = Color.Red)
                    }
                    Button(
                        onClick = {
                            if (query.isNotBlank()) {
                                viewModel.setNewUsername(query)
                                showError = false
                                query = ""
                            } else showError = true
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = YellowMain
                        )
                    ) {
                        Text("Change")
                    }

                }
            }
        }

    }

}