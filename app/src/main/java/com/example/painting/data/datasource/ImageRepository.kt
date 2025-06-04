package com.example.painting.data.datasource

import com.example.painting.data.entities.ImageEntity

interface ImageRepository {
    suspend fun getImageById(id : String, artworkId: Int) : ImageEntity
    suspend fun saveImage(image: ImageEntity)
    suspend fun deleteImage(image: ImageEntity)
}