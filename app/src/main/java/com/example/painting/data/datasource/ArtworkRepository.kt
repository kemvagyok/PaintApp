package com.example.painting.data.datasource

import com.example.painting.data.entities.Artwork
import kotlinx.coroutines.flow.Flow

interface ArtworkRepository {
    suspend fun getArtworks(page: Int) : List<Artwork>
    suspend fun getArtworksByKeyword(keywords: List<String>, page: Int): List<Artwork>
    suspend fun getArtworkId(id : Int) : Artwork
    suspend fun saveArtwork(artwork: Artwork)
    suspend fun deleteArtwork(artwork: Artwork)
    fun getFavoriteArtworksAsFlow(): Flow<List<Artwork>>
    suspend fun getFavouriteArtworkById(id: Int): Artwork?
}