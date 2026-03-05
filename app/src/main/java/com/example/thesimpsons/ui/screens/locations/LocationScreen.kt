package com.example.thesimpsons.ui.screens.locations

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.thesimpsons.R
import com.example.thesimpsons.domain.data_classes.LocationDomain
import com.example.thesimpsons.ui.core.BodyTextItem
import com.example.thesimpsons.ui.core.Header
import com.example.thesimpsons.ui.core.Images
import com.example.thesimpsons.ui.core.ScreenContainer
import com.example.thesimpsons.ui.core.SubtitleItem
import com.example.thesimpsons.ui.theme.Purple40

@Composable
fun LocationScreen(innerPadding: PaddingValues, viewModel: LocationViewModel = hiltViewModel()) {

    val locations = viewModel.locations.collectAsLazyPagingItems()
    val query by viewModel.query.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(locations.loadState) {
        if (locations.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context, "Error: ${(locations.loadState.refresh as LoadState.Error).error.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    ScreenContainer(innerPadding, alignment = Alignment.TopCenter) {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Header(
                query,
                title = "Locations",
                leadingImage = painterResource(R.drawable.lisa_ic),
                trailingImage = painterResource(R.drawable.snake_ic),
                queryPlaceHolder = "Search Location..",
                onQueryChange = { viewModel.updateQuery(it) }
            )

            if (locations.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator()
            } else {
                LazyList(locations)
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
        item {
            if (locations.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun LocationItem(location: LocationDomain) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
                    .aspectRatio(2f),
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

