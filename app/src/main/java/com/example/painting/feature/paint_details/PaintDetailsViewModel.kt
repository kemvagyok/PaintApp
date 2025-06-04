package com.example.painting.feature.paint_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.painting.data.datasource.ArtistRepository
import com.example.painting.data.datasource.ArtworkRepository
import com.example.painting.data.datasource.ImageRepository
import com.example.painting.data.entities.Artist
import com.example.painting.data.entities.Artwork
import com.example.painting.data.entities.ImageEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaintDetailsViewModel @Inject constructor(
    private val artworkRepository: ArtworkRepository,
    private val artistRepository: ArtistRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {

    suspend fun getArtworkById(id: Int): Artwork {
        val favourite = artworkRepository.getFavouriteArtworkById(id)
        if(favourite != null) return favourite
        return artworkRepository.getArtworkId(id)

    }

    suspend fun getImageOfArtwork(artwork: Artwork): ImageEntity? {
        try {
            return imageRepository.getImageById(artwork.imageId.toString(),artwork.id)
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun getArtistOfArtwork(artwork: Artwork): Artist? {
        artwork.artistId?.let {
            return artistRepository.getArtistById(it)
        }
        return null
    }

    fun saveArtwork(artwork: Artwork) {
        viewModelScope.launch(Dispatchers.Default) {
            artworkRepository.saveArtwork(artwork)
        }
    }

    fun saveArtist(artist: Artist) {
        viewModelScope.launch(Dispatchers.Default) {
            artistRepository.saveArtist(artist)
        }
    }

    fun saveImage(imageEntity: ImageEntity) {
        viewModelScope.launch(Dispatchers.Default) {
            imageRepository.saveImage(imageEntity)
        }
    }


    fun removeArtwork(artwork: Artwork) {
        viewModelScope.launch(Dispatchers.Default) {
            artworkRepository.deleteArtwork(artwork)
        }
    }

    fun removeArtist(artist: Artist) {
        viewModelScope.launch(Dispatchers.Default) {
            artistRepository.deleteArtist(artist)
        }
    }

    fun removeImage(imageEntity: ImageEntity) {
        viewModelScope.launch(Dispatchers.Default) {
            imageRepository.deleteImage(imageEntity)
        }
    }
}