package com.example.painting.data.mapper

import com.example.painting.data.entities.Artist
import com.example.painting.network.model.ArtistApi

fun ArtistApi.toEntity(): Artist {
    return Artist(
        id = this.id,
        name = this.title,
        description = this.description
    )

}