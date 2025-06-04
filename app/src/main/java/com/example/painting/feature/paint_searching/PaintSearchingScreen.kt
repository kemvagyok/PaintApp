package com.example.painting.feature.paint_searching

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import com.example.painting.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaintSearchingScreen(
    navController: NavController,
    viewModel: PaintSearchingViewModel
) {
    var searchTitle by remember { mutableStateOf("") }
    var searchAuthor by remember { mutableStateOf("") }
    var searchDescribing by remember { mutableStateOf("") }

    val favouriteImages by viewModel.favouriteImages.collectAsStateWithLifecycle(emptyList())
    val recommendationImages by viewModel.recommendationImages.collectAsStateWithLifecycle(emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = "Art paints searching app"
                    )
                }
            )
        }
    ) {  padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(6.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = if(favouriteImages.isNotEmpty()) "Favourites" else "Recommendation",//FIX IT
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Start)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(if(favouriteImages.isNotEmpty()) favouriteImages else recommendationImages) { imageEntity ->
                    Image(
                        bitmap = imageEntity.imageData.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 120.dp, height = 180.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .clickable {
                                viewModel.onArtworkClicked(imageEntity.artworkId.toString())
                                navController.navigate(
                                    Screen.ArtworkDetails.createRoute(
                                        imageEntity.artworkId
                                    )
                                )
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.padding(10.dp)
            ){
                Column {
                    Text(
                        text = "Searching",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    SearchField(label = "Title", searchTitle, { newValue -> searchTitle = newValue})
                    SearchField(label = "Author", searchAuthor, { newValue -> searchAuthor = newValue})
                    SearchField(label = "Describing", searchDescribing, { newValue -> searchDescribing = newValue})
                }
               }


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.onSearchButtonClicked()
                    navController.navigate(Screen.ArtworkList.createRoute(searchTitle,searchAuthor,searchDescribing))
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(48.dp)
            ) {
                Text(text = "Searching", color = MaterialTheme.colorScheme.primary)
            }
        }

    }

}

@Composable
fun SearchField(label: String, searchingValue: String, onSearchValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchingValue,
        onValueChange = onSearchValueChange,
        label = { Text(text = label) },
        placeholder = { Text("Input") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(
                MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(8.dp)
            )
    )
}


