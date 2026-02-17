package com.example.thesimpsons.ui.screens.characterdetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.thesimpsons.domain.CharacterDomain
import com.example.thesimpsons.ui.core.BodyTextItem
import com.example.thesimpsons.ui.core.Images
import com.example.thesimpsons.ui.core.InfoTextItem
import com.example.thesimpsons.ui.core.ScreenContainer
import com.example.thesimpsons.ui.core.SubtitleItem
import com.example.thesimpsons.ui.core.TitleItem
import com.example.thesimpsons.ui.screens.home.UiState
import com.example.thesimpsons.ui.theme.DarkBackgroundApp
import com.example.thesimpsons.ui.theme.DarkBackgroundCard
import com.example.thesimpsons.ui.theme.DarkText
import com.example.thesimpsons.ui.theme.GreenApp
import com.example.thesimpsons.ui.theme.LightBackgroundApp
import com.example.thesimpsons.ui.theme.LightBackgroundCard
import com.example.thesimpsons.ui.theme.LightText

@Composable
fun CharacterDetailsScreen(
    innerPadding: PaddingValues,
    characterId: Int,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCharacterDetails(characterId)
    }

    when (uiState) {
        is UiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${(uiState as UiState.Error).error.message}")
            }
        }

        UiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            Details((uiState as UiState.Success).character, innerPadding) { onBackClick() }
        }
    }
}

@Composable
fun Details(character: CharacterDomain, innerPadding: PaddingValues, onBackClick: () -> Unit) {

    val backgroundCard = if (isSystemInDarkTheme()) DarkBackgroundCard else LightBackgroundCard
    val borderImageColor = if (character.isAlive) GreenApp else Color.Red
    val textColor = if (isSystemInDarkTheme()) DarkText else LightText
    val aliveText = "ALIVE"
    val deadText = "DEAD"

    ScreenContainer(innerPadding) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "back button",
                    tint = textColor,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .clickable {
                            onBackClick()
                        })
            }
            Box(Modifier.size(280.dp), contentAlignment = Alignment.BottomCenter) {
                Card(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = backgroundCard),
                    border = BorderStroke(5.dp, borderImageColor),
                    elevation = CardDefaults.cardElevation(16.dp)
                ) {
                    AsyncImage(
                        model = Images.createPath(Images.PORTRAIT_SIZE, character.portraitImage),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                    )
                }
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = if (character.isAlive) GreenApp else Color.Red)
                ) {
                    Text(
                        if (character.isAlive) aliveText else deadText,
                        modifier = Modifier.padding(8.dp),
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
            TitleItem(
                text = character.name,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                Card(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                    modifier = Modifier.padding(top = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = backgroundCard),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoTextItem(
                            "Age:",
                            if (character.age != 0) character.age.toString() else "Unknown"
                        )
                        InfoTextItem("Birthdate:", character.birthdate)
                        InfoTextItem("Gender:", character.gender)
                        InfoTextItem("Occupation:", character.occupation)
                        if (character.phrases.isNotEmpty()) {
                            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                SubtitleItem("Iconic phrases")
                            }
                        }
                        character.phrases.forEach {
                            BodyTextItem(it)
                        }
                    }

                }
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = backgroundCard)
                ) {
                    SubtitleItem(text = "INFORMATION", modifier =  Modifier.padding(16.dp))
                }

            }

        }
    }


}
