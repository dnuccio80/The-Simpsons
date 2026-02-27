package com.example.thesimpsons.ui.screens.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.thesimpsons.R
import com.example.thesimpsons.domain.data_classes.CharacterDomain
import com.example.thesimpsons.ui.core.Header
import com.example.thesimpsons.ui.core.Images
import com.example.thesimpsons.ui.core.ScreenContainer
import com.example.thesimpsons.ui.theme.DarkBackgroundCard
import com.example.thesimpsons.ui.theme.GreenApp
import com.example.thesimpsons.ui.theme.LightBackgroundCard

@Composable
fun CharactersScreen(
    innerPadding: PaddingValues,
    viewModel: CharacterViewModel = hiltViewModel(),
    onNavigateToDetails: (Int) -> Unit,
) {

    val characters = viewModel.characters.collectAsLazyPagingItems()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val darkMode by viewModel.darkMode.collectAsStateWithLifecycle()

    ScreenContainer(innerPadding,alignment = Alignment.TopCenter) {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Header(
                query,
                title = "Characters",
                leadingImage = painterResource(R.drawable.bart_ic),
                trailingImage = painterResource(R.drawable.marge_ic),
                queryPlaceHolder = "Search character..",
                onQueryChange = { viewModel.updateQuery(it) }
            )
            when (characters.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator()
                }

                is LoadState.Error -> {
                    Text("Error loading characters")
                }

                is LoadState.NotLoading -> {
                    CharacterList(characters, darkMode) { onNavigateToDetails(it) }
                }
            }
        }

    }
}

@Composable
fun CharacterList(characters: LazyPagingItems<CharacterDomain>, darkMode: Boolean, onItemClick: (Int) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        state = rememberLazyGridState(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            characters.itemCount,
            key = { index -> characters[index]?.id ?: index }
        ) { index ->
            characters[index]?.let { character ->
                ItemList(character, darkMode) { onItemClick(character.id) }
            }
        }
    }
}

@Composable
fun ItemList(character: CharacterDomain, darkMode: Boolean, onClick: () -> Unit) {

    val backgroundCardColor = if (darkMode) DarkBackgroundCard else LightBackgroundCard

    Card(
        Modifier
            .size(200.dp)
            .clickable { onClick() },
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