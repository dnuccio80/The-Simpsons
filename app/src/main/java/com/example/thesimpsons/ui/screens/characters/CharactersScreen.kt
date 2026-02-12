package com.example.thesimpsons.ui.screens.characters

import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.example.thesimpsons.domain.CharacterDomain

@Composable
fun CharactersScreen(innerPadding: PaddingValues, viewModel: CharacterViewModel = hiltViewModel()) {

    val characters = viewModel.characters.collectAsLazyPagingItems()

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
                    .background(Color.Gray), contentAlignment = Alignment.Center
            ) {
                CharacterList(characters)
            }
        }
    }


}

@Composable
fun CharacterList(characters: LazyPagingItems<CharacterDomain>) {

    LazyVerticalGrid (
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
                ItemList(it)
            }
        }
    }
}

@Composable
fun ItemList(character: CharacterDomain) {
    Box(
        Modifier
            .size(200.dp)
            .background(Color.Blue)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(character.name)
    }
}