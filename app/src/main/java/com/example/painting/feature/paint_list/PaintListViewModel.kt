package com.example.painting.feature.paint_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.painting.data.datasource.ArtworkRepository
import com.example.painting.data.datasource.ImageRepository
import com.example.painting.data.entities.Artwork
import com.example.painting.data.entities.ImageEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.Regex

@HiltViewModel
class PaintListViewModel @Inject constructor(
    private val artworkRepository: ArtworkRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {
    val _artworks = MutableStateFlow<List<Artwork>>(emptyList())
    val artworks = _artworks.asStateFlow()
    private val _artworkImages = MutableStateFlow<Map<Int, ImageEntity>>(emptyMap())
    val artworkImages  = _artworkImages.asStateFlow()

    suspend fun getArtWorksList(title: String, author: String, describing: String, page: Int) {
        if(title.isEmpty() && author.isEmpty() && describing.isEmpty())
        {
            artworkRepository.getArtworks(page = page).forEach {
                _artworks.value+=it
            }

        } else {
            _artworks.value+=artworkRepository.getArtworksByKeyword(listOf(title, author, describing), page)
                .filter { it.title.contains(Regex(title)) }
                .filter { it.artistName?.contains(Regex(author)) == true }
                .filter { it.description?.contains(Regex(describing)) == true }
        }
        preloadImages(_artworks.value)
    }


    private fun preloadImages(artworks: List<Artwork>) {
        viewModelScope.launch {
            val imagesList = artworks.mapNotNull { getImageOfArtwork(it) }  // minden kép lekérése
            val imageMap = imagesList.associateBy { it.artworkId }   // kulcs: imageEntity.artWorkId
            _artworkImages.value = imageMap
        }
    }

    private suspend fun getImageOfArtwork(artwork: Artwork): ImageEntity? {
        try {
            return imageRepository.getImageById(artwork.imageId.toString(),artwork.id)
        } catch (e: Exception) {
            return null
        }
    }


}