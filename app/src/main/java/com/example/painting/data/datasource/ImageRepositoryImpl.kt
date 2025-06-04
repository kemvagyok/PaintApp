package com.example.painting.data.datasource

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.painting.data.dao.ImageDao
import com.example.painting.data.entities.ImageEntity
import com.example.painting.network.api.ImageService
import okio.IOException
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageService : ImageService,
    private val imageDao: ImageDao
) : ImageRepository {
    override suspend fun getImageById(id: String, artworkId: Int): ImageEntity {
        val response = imageService.downloadImage("https://www.artic.edu/iiif/2/$id/full/843,/0/default.jpg")
        val imageBitmap = response.body()?.byteStream()?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        } ?: throw IOException("Sikertelen letöltés az ID-val: $id")

        return ImageEntity(id, artworkId, imageBitmap)
    }

    override suspend fun saveImage(imageEntity: ImageEntity) {
        imageDao.insertImage(imageEntity)
    }

    override suspend fun deleteImage(image: ImageEntity) {
        imageDao.deleteImage(image)
    }


}