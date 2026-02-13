package com.example.thesimpsons.ui.screens.characters

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import com.example.thesimpsons.domain.CharacterDomain
import com.example.thesimpsons.ui.core.Images
import com.example.thesimpsons.ui.theme.DarkBackgroundApp
import com.example.thesimpsons.ui.theme.DarkBackgroundCard
import com.example.thesimpsons.ui.theme.DarkText
import com.example.thesimpsons.ui.theme.GreenApp
import com.example.thesimpsons.ui.theme.LightBackgroundApp
import com.example.thesimpsons.ui.theme.LightBackgroundCard
import com.example.thesimpsons.ui.theme.LightText

@Composable
fun CharactersScreen(innerPadding: PaddingValues, viewModel: CharacterViewModel = hiltViewModel(), onNavigateToDetails:(Int) -> Unit) {

    val characters = viewModel.characters.collectAsLazyPagingItems()

    val backgroundScreen = if (isSystemInDarkTheme()) DarkBackgroundApp else LightBackgroundApp

    when (characters.loadState.refresh) {
        is LoadState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is LoadState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error loading characters")
            }
        }

        is LoadState.NotLoading -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(backgroundScreen), contentAlignment = Alignment.Center
            ) {
                CharacterList(characters) { onNavigateToDetails(it) }
            }
        }
    }
}

@Composable
fun CharacterList(characters: LazyPagingItems<CharacterDomain>, onItemClick:(Int) -> Unit) {

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            characters.itemCount,
            key = { index -> characters[index]?.id ?: index }
        ) { index ->
            characters[index]?.let {
                ItemList(it) { onItemClick(it.id) }
            }
        }
    }
}

@Composable
fun ItemList(character: CharacterDomain, onClick:() -> Unit) {

    val backgroundCardColor = if (isSystemInDarkTheme()) DarkBackgroundCard else LightBackgroundCard

    Card(
        Modifier
            .size(200.dp)
            .clickable { onClick() }
        ,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundCardColor
        )
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            AsyncImage(
                model = Images.createPath(Images.PORTRAIT_SIZE, character.portraitImage),
                contentDescription = "character image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(GreenApp.copy(.9f)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    character.name,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold,
                )
            }

        }
    }
}