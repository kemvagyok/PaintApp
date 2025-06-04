package com.example.painting.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artworks")
data class Artwork(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val description: String?,
    val artistId: Int?,
    val artistName: String?,
    val imageId: String?,
    var isFavourite : Boolean = false
)