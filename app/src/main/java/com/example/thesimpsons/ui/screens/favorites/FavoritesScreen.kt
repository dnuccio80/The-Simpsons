package com.example.thesimpsons.ui.screens.favorites

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.example.thesimpsons.R
import com.example.thesimpsons.domain.data_classes.CharacterDomain
import com.example.thesimpsons.ui.core.BodyTextItem
import com.example.thesimpsons.ui.core.ScreenContainer
import com.example.thesimpsons.ui.core.SubtitleItem
import com.example.thesimpsons.ui.navigation.NavRoutes
import com.example.thesimpsons.ui.navigation.NavigationItem
import com.example.thesimpsons.ui.screens.characters.ItemList
import com.example.thesimpsons.ui.theme.GreenApp

@Composable
fun FavoritesScreen(innerPadding: PaddingValues, viewModel: FavoritesViewModel = hiltViewModel()) {

    val darkMode by viewModel.darkMode.collectAsStateWithLifecycle()
    val characters = viewModel.characters.collectAsLazyPagingItems()

    val selectableList = listOf(
        NavigationItem.Characters.name,
        NavigationItem.Episodes.name,
        NavigationItem.Locations.name
    )

    var selectedSlider by rememberSaveable { mutableStateOf(selectableList.first()) }

    ScreenContainer(innerPadding) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Header()
            Spacer(Modifier.size(0.dp))
            Sliders(selectableList, selectedSlider)
            Section(darkMode = darkMode, characters) { }
        }
    }

}

@Composable
private fun Sliders(
    selectableList: List<String>,
    selectedSlider: String,
) {
    var selectedSlider1 = selectedSlider
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            selectableList.forEach { selectableName ->
                SelectableSlider(selectableName, selectedSlider1) {
                    selectedSlider1 = selectableName
                }
            }
        }
        Spacer(Modifier.size(4.dp))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            thickness = 2.dp
        )
    }
}

@Composable
private fun SelectableSlider(title: String, selected: String, onClick: () -> Unit) {

    val color by animateColorAsState(targetValue = if (selected == title) GreenApp else Color.Transparent)

    Box(
        Modifier
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        BodyTextItem(title, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
    }
}

@Composable
private fun Section(
    darkMode: Boolean,
    list: LazyPagingItems<CharacterDomain>,
    onItemClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        state = rememberLazyGridState(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            list.itemCount,
            key = { index -> list[index]?.id ?: index }
        ) { index ->
            list[index]?.let { character ->
                ItemList(character, darkMode) { onItemClick(character.id) }
            }
        }
    }
}

@Composable
private fun Header() {
    Card(
        Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            Modifier
                .fillMaxSize(), contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painterResource(R.drawable.favorites_img),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GreenApp.copy(alpha = .8f)),
                contentAlignment = Alignment.Center
            ) {
                SubtitleItem("My Favorites", whiteColor = true, modifier = Modifier.padding(8.dp))
            }
        }
    }
}