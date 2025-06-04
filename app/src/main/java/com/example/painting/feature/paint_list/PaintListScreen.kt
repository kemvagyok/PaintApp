package com.example.painting.feature.paint_list

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.painting.ui.common.PaintCard
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.painting.navigation.Screen
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaintListScreen(
    navController: NavController,
    viewModel: PaintListViewModel,
    title: String,
    author: String,
    describing: String
) {
    val configuration = LocalConfiguration.current
    val artworks by viewModel.artworks.collectAsStateWithLifecycle()
    val images by viewModel.artworkImages.collectAsStateWithLifecycle()
    var isLaunhced by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var page by remember { mutableStateOf(1) }
    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()



    LaunchedEffect(lazyListState) {
        viewModel.getArtWorksList(title, author, describing, page)
        isLaunhced = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Searching")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },

                )
        },
    ) { padding ->
        if(artworks.isEmpty() && images.isEmpty() && !isLaunhced) {
            val infiniteTransition = rememberInfiniteTransition()
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
            Box(modifier = Modifier.padding(padding)) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Loading",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(500.dp)
                        .rotate(rotation)
                        .padding(100.dp),

                )
            }

        }
        else if(artworks.isEmpty() && images.isEmpty() && isLaunhced)
        {
            Box(modifier = Modifier.padding(padding)) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Loading",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(500.dp)
                            .padding(horizontal = 5.dp, vertical = 100.dp),

                        )
                    Text("Not found!",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
        else {
            when (configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> {

                    LazyColumn(state = lazyListState) {
                        items(artworks) { artwork ->
                            val imageEntity = images[artwork.id]
                            if (imageEntity != null) {
                                PaintCard(
                                    artwork = artwork,
                                    image = imageEntity.imageData,
                                    toArtworkAction = { navController.navigate(Screen.ArtworkDetails.createRoute(artwork.id)) }
                                )
                            }
                        }
                    }
                    LaunchedEffect(lazyListState) {
                        snapshotFlow { lazyListState.layoutInfo}
                            .collect { layoutInfo ->
                                val totalItems = layoutInfo.totalItemsCount
                                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index
                                val isAtEnd = lastVisibleItemIndex!! >= totalItems - 5 // ha már közel vagyunk a végéhez
                                val canScroll = layoutInfo.visibleItemsInfo.size < totalItems // valóban kell scrollozni

                                if (isAtEnd && canScroll && !isLoading) {
                                    isLoading = true
                                    val previousSize = viewModel.artworks.value.size
                                    viewModel.getArtWorksList(title, author, describing, page)
                                    page++
                                    isLoading = previousSize == viewModel.artworks.value.size
                                }
                            }
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        state = lazyGridState,
                        columns = GridCells.Adaptive(minSize = 350.dp),
                    ) {
                        items(artworks) { artwork ->
                            val imageEntity = images[artwork.id]
                            if (imageEntity != null) {
                                PaintCard(
                                    artwork = artwork,
                                    image = imageEntity.imageData,
                                    toArtworkAction = { navController.navigate(Screen.ArtworkDetails.createRoute(artwork.id)) }
                                )
                            }

                        }
                    }
                    LaunchedEffect(lazyGridState) {
                        snapshotFlow { lazyGridState.layoutInfo}
                            .collect { layoutInfo ->
                                val totalItems = layoutInfo.totalItemsCount
                                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index
                                val isAtEnd = lastVisibleItemIndex!! >= totalItems - 5 // ha már közel vagyunk a végéhez
                                val canScroll = layoutInfo.visibleItemsInfo.size < totalItems // valóban kell scrollozni

                                if (isAtEnd && canScroll && !isLoading) {
                                    isLoading = true
                                    val previousSize = viewModel.artworks.value.size
                                    viewModel.getArtWorksList(title, author, describing, page)
                                    page++
                                    isLoading = previousSize == viewModel.artworks.value.size
                                }
                            }
                    }
                }
            }
        }
    }
}

