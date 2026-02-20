package com.example.thesimpsons.ui.screens.locations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.thesimpsons.R
import com.example.thesimpsons.domain.LocationDomain
import com.example.thesimpsons.ui.core.BodyTextItem
import com.example.thesimpsons.ui.core.Images
import com.example.thesimpsons.ui.core.QuerySearchItem
import com.example.thesimpsons.ui.core.ScreenContainer
import com.example.thesimpsons.ui.core.SubtitleItem
import com.example.thesimpsons.ui.core.TitleItem
import com.example.thesimpsons.ui.theme.Purple40

@Composable
fun LocationScreen(innerPadding: PaddingValues, viewModel: LocationViewModel = hiltViewModel()) {

    val locations = viewModel.locations.collectAsLazyPagingItems()
    val query by viewModel.query.collectAsState()

    when (locations.loadState.refresh) {
        is LoadState.Error -> {
            Text("Error getting locations")
        }

        LoadState.Loading -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }

        is LoadState.NotLoading -> {
            ScreenContainer(innerPadding) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp, top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Image(painterResource(R.drawable.bart_ic), "", Modifier.size(60.dp))
                        Spacer(Modifier.size(4.dp))
                        TitleItem("Characters")
                        Spacer(Modifier.size(4.dp))
                        Image(painterResource(R.drawable.marge_ic), "", Modifier.size(60.dp))
                    }
                    QuerySearchItem(query, "Search location..") { value -> viewModel.updateQuery(value) }
                    LazyList(locations)
                }
            }
        }
    }

}

@Composable
fun LazyList(locations: LazyPagingItems<LocationDomain>) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = rememberLazyListState()
    ) {
        items(
            locations.itemCount,
            key = { index -> locations[index]?.id ?: index })
        { index ->
            locations[index]?.let {
                LocationItem(it)
            }
        }
    }
}

@Composable
fun LocationItem(location: LocationDomain) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column {
            AsyncImage(
                model = Images.createPath(Images.LOCATION_IMAGE, location.imagePath),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Purple40)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    SubtitleItem(location.name, modifier = Modifier.padding(vertical = 4.dp))
                    BodyTextItem("${location.town} - ${location.use}")
                }
            }
        }


    }

}

