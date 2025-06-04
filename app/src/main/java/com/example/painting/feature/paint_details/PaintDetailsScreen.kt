package com.example.painting.feature.paint_details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.example.painting.data.entities.Artist
import com.example.painting.data.entities.Artwork
import com.example.painting.data.entities.ImageEntity
import com.example.painting.ui.common.PaintDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaintDetailsScreen(
    navController: NavController,
    viewModel: PaintDetailsViewModel,
    artworkId : Int) {

    val configuration = LocalConfiguration.current

    var artwork : Artwork? by remember { mutableStateOf(null) }
    var imageEntity : ImageEntity? by remember { mutableStateOf(null) }
    var artist : Artist? by remember { mutableStateOf(null) }

    var isFavourite by remember { mutableStateOf(false)}

    LaunchedEffect(Unit) {
        artwork = viewModel.getArtworkById(artworkId)
        isFavourite = artwork?.isFavourite == true
        artwork?.let {
            imageEntity = viewModel.getImageOfArtwork(it)
            artist = viewModel.getArtistOfArtwork(it)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Describing") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if(artwork?.isFavourite == false) {
                            artwork?.isFavourite = true
                            isFavourite = true
                            artwork?.let {
                                viewModel.saveArtwork(it)
                            }
                            imageEntity?.let {
                                viewModel.saveImage(it)
                            }
                            artist?.let {
                                viewModel.saveArtist(it)
                            }
                        } else {
                            artwork?.isFavourite = false
                            isFavourite = false
                            artwork?.let {
                                viewModel.removeArtwork(it)
                            }
                            imageEntity?.let {
                                viewModel.removeImage(it)
                            }
                            artist?.let {
                                viewModel.removeArtist(it)
                            }
                        }

                    }) {
                        if(isFavourite == true) {
                            Icon(Icons.Default.Star, contentDescription = "Favourite")
                        } else {
                            Icon(Icons.TwoTone.Star, contentDescription = "Favourite")
                        }
                    }
                }
            )
        } ) { padding ->
        when (configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PaintDetail(imageEntity,artwork,artist)
                }
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                Row(
                    modifier = Modifier
                        .padding(padding)

                ) {
                    PaintDetail(imageEntity,artwork,artist)
                }
            }
        }

    }
}

