package com.example.thesimpsons.ui.screens.episodedetails

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.thesimpsons.domain.EpisodeDomain
import com.example.thesimpsons.ui.core.BodyTextItem
import com.example.thesimpsons.ui.core.Images
import com.example.thesimpsons.ui.core.InfoTextItem
import com.example.thesimpsons.ui.core.ScreenContainer
import com.example.thesimpsons.ui.core.SubtitleItem
import com.example.thesimpsons.ui.core.TitleItem
import com.example.thesimpsons.ui.theme.DarkText
import com.example.thesimpsons.ui.theme.LightText

@Composable
fun EpisodeDetailsScreen(
    id: Int,
    innerPadding: PaddingValues,
    viewModel: EpisodeDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEpisodeInfo(id)
    }

    ScreenContainer(innerPadding) {
        when (uiState) {
            is EpisodeUiState.Error -> {
                BodyTextItem("Error: ${(uiState as EpisodeUiState.Error).error}")
            }

            EpisodeUiState.Loading -> {
                CircularProgressIndicator()
            }

            is EpisodeUiState.Success -> {
                EpisodeInformation((uiState as EpisodeUiState.Success).episode) { onBackClick() }
            }
        }
    }
}

@Composable
fun EpisodeInformation(episode: EpisodeDomain, onBackClick: () -> Unit) {

    val textColor = if (isSystemInDarkTheme()) DarkText else LightText
    Column(Modifier.fillMaxSize().padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            Icon(
                Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "back button",
                tint = textColor,
                modifier = Modifier
                    .clickable {
                        onBackClick()
                    })
        }
        AsyncImage(
            model = Images.createPath(Images.PORTRAIT_SIZE, episode.imagePath),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleItem(episode.name)
                SubtitleItem("Information")
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoTextItem("Air date:", episode.airDate)
            InfoTextItem("Season:", episode.season)
            InfoTextItem("Episode number:", episode.episodeNumber)
            if (episode.synopsis.isNotBlank()) {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    SubtitleItem("Synopsis")
                }
                BodyTextItem(episode.synopsis)
            }
        }
    }
}

