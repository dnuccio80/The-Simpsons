package com.danucdev.thesimpsons.ui.screens.locations

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.danucdev.thesimpsons.R
import com.danucdev.thesimpsons.domain.data_classes.LocationDomain
import com.danucdev.thesimpsons.ui.core.BodyTextItem
import com.danucdev.thesimpsons.ui.core.EmptyLazyListError
import com.danucdev.thesimpsons.ui.core.Header
import com.danucdev.thesimpsons.ui.core.Images
import com.danucdev.thesimpsons.ui.core.ScreenContainer
import com.danucdev.thesimpsons.ui.core.SubtitleItem
import com.danucdev.thesimpsons.ui.theme.Purple40

@Composable
fun LocationScreen(innerPadding: PaddingValues, viewModel: LocationViewModel = hiltViewModel()) {

    val locations = viewModel.locations.collectAsLazyPagingItems()
    val query by viewModel.query.collectAsState()
    var itemsError by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(locations.itemSnapshotList) {
        itemsError = locations.itemSnapshotList.isEmpty()
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
                if (itemsError) {
                    EmptyLazyListError()
                } else {
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

