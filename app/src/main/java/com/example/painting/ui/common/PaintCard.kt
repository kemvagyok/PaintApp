package com.example.painting.ui.common

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.painting.data.entities.Artwork
import com.example.painting.ui.theme.PaintingTheme
import androidx.core.graphics.createBitmap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaintCard(artwork: Artwork, image: Bitmap, toArtworkAction: () -> Unit) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(35.dp)
    ) {

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = artwork.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                            .clickable { toArtworkAction() },
                        contentScale = ContentScale.Crop
                    )
                }
                Box(modifier = Modifier.padding(10.dp)) { Text(
                    artwork.title,
                    fontWeight = FontWeight.Bold
                ) }
            }

        }
    }

@Preview
@Composable
fun kinezetPaintCard() {
    PaintingTheme {
        PaintCard(
            Artwork(1, "Title","Description", null, null, null),
            image = createBitmap(100, 100),
            toArtworkAction = {}
        )
    }
}
