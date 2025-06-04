package com.example.painting.feature.paint_searching

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.painting.data.analytics.AnalyticsLogger
import com.example.painting.data.datasource.ArtworkRepository
import com.example.painting.data.datasource.ImageRepository
import com.example.painting.data.entities.ImageEntity
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaintSearchingViewModel @Inject constructor(
    private val artworkRepository: ArtworkRepository,
    private val imageRepository: ImageRepository,
    private val analyticsLogger: AnalyticsLogger
) : ViewModel() {

    private val _favouriteImages = MutableStateFlow<List<ImageEntity>>(emptyList())
    val favouriteImages = _favouriteImages.asStateFlow()
    private val _recommendationImages = MutableStateFlow<List<ImageEntity>>(emptyList())
    val recommendationImages = _recommendationImages.asStateFlow()

    private val favourites = artworkRepository.getFavoriteArtworksAsFlow()


    init {
        viewModelScope.launch {
            favourites.collect { artworks ->
                if (artworks.isEmpty()) {
                    _favouriteImages.value = emptyList()
                   try {
                        val artworksFromApi = artworkRepository.getArtworks(page = 1)
                        val iterator = artworksFromApi.iterator()
                        while (_recommendationImages.value.size < 6 && iterator.hasNext()) {
                            val artwork = iterator.next()
                            val image = runCatching {
                                imageRepository.getImageById(artwork.imageId.toString(), artwork.id)

                            }.getOrNull()

                            if(image != null) {
                                _recommendationImages.value += image
                            }
                        }

                    } catch (e: Exception) {

                    }
                } else {
                    val images = artworks.mapNotNull { artwork ->
                        runCatching {
                            imageRepository.getImageById(artwork.imageId.toString(), artwork.id)
                        }.getOrNull()
                    }
                    _favouriteImages.value = images
                    //throw RuntimeException("Test Crash")
                }
            }
        }
    }

    fun onArtworkClicked(artworkId: String) {
        analyticsLogger.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, artworkId)
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Artwork")
        })
    }

    fun onSearchButtonClicked() {
        analyticsLogger.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, "Listing paints")
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "PaintListScreen")
        })
    }
}
