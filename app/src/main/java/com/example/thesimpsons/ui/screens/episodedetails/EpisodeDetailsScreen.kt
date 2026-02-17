package com.example.thesimpsons.ui.screens.episodedetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun EpisodeDetailsScreen(id:Int, innerPadding: PaddingValues, viewModel: EpisodeDetailsViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEpisodeInfo(id)
    }



}