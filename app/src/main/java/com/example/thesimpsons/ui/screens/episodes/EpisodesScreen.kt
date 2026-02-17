package com.example.thesimpsons.ui.screens.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.thesimpsons.domain.EpisodeDomain
import com.example.thesimpsons.ui.core.Images
import com.example.thesimpsons.ui.core.ScreenContainer
import com.example.thesimpsons.ui.theme.GreenApp

@Composable
fun EpisodesScreen(innerPadding: PaddingValues, viewModel: EpisodesViewModel = hiltViewModel()) {

    val episodes = viewModel.episodeList.collectAsLazyPagingItems()

    when (episodes.loadState.refresh) {
        is LoadState.Error -> {
            Text("Error loading episodes")
        }

        LoadState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is LoadState.NotLoading -> {
            ScreenContainer(innerPadding) {
                LazyEpisodesColumn(episodes)
            }
        }
    }
}

@Composable
fun LazyEpisodesColumn(episodes: LazyPagingItems<EpisodeDomain>) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = rememberLazyListState()
    ) {
        items(
            episodes.itemCount,
            key = { index -> episodes[index]?.id ?: index }) { index ->
            episodes[index]?.let {
                EpisodeItem(it)
            }
        }
    }

}

@Composable
fun EpisodeItem(episode: EpisodeDomain) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 8.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            AsyncImage(
                model = Images.createPath(Images.PORTRAIT_SIZE, episode.imagePath),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,

            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(GreenApp.copy(alpha = .9f)), contentAlignment = Alignment.Center
            ) {
                Text(
                    "Season: ${episode.season}: ${episode.name}",
                    modifier = Modifier.padding(8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

    }

}