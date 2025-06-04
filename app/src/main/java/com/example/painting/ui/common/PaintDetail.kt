package com.example.painting.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import com.example.painting.data.entities.Artist
import com.example.painting.data.entities.Artwork
import com.example.painting.data.entities.ImageEntity
import com.example.painting.ui.theme.PaintingTheme

@Composable
fun PaintDetail(imageEntity : ImageEntity?, artwork: Artwork?, artist: Artist?) {
        imageEntity?.let {
            Card(
                modifier = Modifier.padding(5.dp)
            ) {
                Image(
                    bitmap = it.imageData.asImageBitmap(),
                    contentDescription = artwork?.title.toString(),
                    modifier = Modifier.padding(10.dp),

                )
            }

        }
        Column(
            modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 10.dp, horizontal = 15.dp)
    ) {
            Card(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(vertical = 8.dp, horizontal = 15.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Title",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        ),
                    )
                    Text(artwork?.title ?: "Without name", fontSize = 20.sp)
                }

            }
            Card(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(vertical = 8.dp, horizontal = 15.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Describing",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        ),
                    )
                    Text(artwork?.description ?: "Without describing")
                }

            }
            Card(
                modifier = Modifier
                    .padding(5.dp),
            )
             {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(vertical = 8.dp, horizontal = 15.dp)
                        .fillMaxWidth(),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Author",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        ),
                    )
                    Text(artist?.name ?: "Anonymous/Not given")
                }
            }
        }

}

@Preview
@Composable
fun KinezetPaintDetailColumn() {
    PaintingTheme {
        Column(
            modifier = Modifier.padding(PaddingValues(3.dp))
        ) {
            PaintDetail(
                imageEntity = ImageEntity("", 0,createBitmap(100, 100)),
                artwork =  Artwork(1, "Title","Description", null, null, null),
                artist = Artist(0, "Kiskunfélegyházi Pistabácsi", "Szereti a hagymát és a kenyeret"),
            )
        }

    }
}

@Preview
@Composable
fun KinezetPaintDetailRow() {
    PaintingTheme {
        Row(
            modifier = Modifier.padding(PaddingValues(3.dp))
        ) {
            PaintDetail(
                imageEntity = ImageEntity("", 0,createBitmap(100, 100)),
                artwork =  Artwork(1, "Title","Description", null, null, null),
                artist = Artist(0, "Kiskunfélegyházi Pistabácsi", "Szereti a hagymát és a kenyeret"),
            )
        }

    }
}