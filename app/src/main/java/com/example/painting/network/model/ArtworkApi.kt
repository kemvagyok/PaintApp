package com.example.painting.network.model

data class ArtworkApi(
    val id : Int,
    val title : String,
    val description : String?,
    val image_id : String?,
    val artist_id : Int?,
    val artist_title: String?
)