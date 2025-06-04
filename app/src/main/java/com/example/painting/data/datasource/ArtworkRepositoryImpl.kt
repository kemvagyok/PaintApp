package com.example.painting.data.datasource

import androidx.compose.animation.core.rememberTransition
import com.example.painting.data.dao.ArtworkDao
import com.example.painting.data.entities.Artwork
import com.example.painting.data.mapper.toEntity
import com.example.painting.network.api.ArtworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArtworkRepositoryImpl @Inject constructor(
    private val artworkService : ArtworkService,
    private val artworkDao: ArtworkDao
) : ArtworkRepository {
    override suspend fun getArtworks(page: Int): List<Artwork> {
        return artworkService.getArtworksApi(page = page).data.map {
            it.toEntity()
        }
    }
    override suspend fun getArtworksByKeyword(keywords: List<String>, page: Int): List<Artwork> {
        var artworks = mutableListOf<Artwork>()
        artworkService.getArtworksApiSearching(keywords = keywords.filter({it.isNotEmpty()}).joinToString(" "), page = page).data.map {
            var artworkApi = artworkService.getArtworkApi(it.id)
            var artwork = artworkApi.data.toEntity()
            artworks.add(artwork)
        }
        return artworks
    }

    override suspend fun getArtworkId(id: Int): Artwork {
        return artworkService.getArtworkApi(id).data.toEntity()
    }
        
    override fun getFavoriteArtworksAsFlow(): Flow<List<Artwork>>{
        return artworkDao.getArtworksAll()

    }

    override suspend fun getFavouriteArtworkById(id: Int): Artwork? {
        return artworkDao.getArtworkById(id)
    }

    override suspend fun saveArtwork(artwork: Artwork) {
        withContext(Dispatchers.IO) {
            artworkDao.insertArtwork(artwork)
        }
    }

    override suspend fun deleteArtwork(artwork: Artwork) {
        withContext(Dispatchers.IO) {
            artworkDao.deleteArtwork(artwork)
        }
    }


}