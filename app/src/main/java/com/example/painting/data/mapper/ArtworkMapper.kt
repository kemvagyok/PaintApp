package com.example.painting.data.mapper

import com.example.painting.data.entities.Artwork
import com.example.painting.network.model.ArtworkApi

fun ArtworkApi.toEntity(): Artwork {
    return Artwork(
        id = this.id,
        title = this.title,
        artistId = this.artist_id,
        artistName = this.artist_title,
        description = this.description?.replace(Regex("<p>"),"")?.replace("</p>",""),
        imageId = this.image_id,
    )
}

