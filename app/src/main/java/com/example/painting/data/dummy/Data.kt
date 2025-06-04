package com.example.painting.data.dummy

import android.graphics.Bitmap
import androidx.core.graphics.createBitmap
import com.example.painting.data.entities.Artist
import com.example.painting.data.entities.Artwork
import com.example.painting.data.entities.ImageEntity
import com.example.painting.network.model.ApiResponse
import com.example.painting.network.model.ApiResponseWithPagination
import com.example.painting.network.model.ArtistApi
import com.example.painting.network.model.ArtworkApi
import com.example.painting.network.model.Pagination
import com.example.painting.network.model.SearchingApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object Data {

    //Artwork
    //-Api
    fun createSingleArtworkApi(id: Int = 1): ApiResponse<ArtworkApi> =
        ApiResponse(data = createArtworkApi(id))
    fun createArtworkListApi(page: Int = 1): ApiResponseWithPagination<List<ArtworkApi>> =
        ApiResponseWithPagination(
            pagination = createPagination(page),
            data = listOf(
                createArtworkApi(1),
                createArtworkApi(2)
            )
        )
    //-Dao
    fun createSingleArtworkDao(id: Int = 1): Artwork = createArtwork(id)
    fun createArtworkListDao(): Flow<List<Artwork>> = flow {
        emit(listOf<Artwork>(
            createArtwork(1, true),
            createArtwork(2, true),
            createArtwork(3, true),
        ))

    }

    //Artist
    //-Api
    fun createSingleArtistApi(id: Int = 1): ApiResponse<ArtistApi> = ApiResponse(data = createArtistApi(id))
    fun createArtistListApi(page: Int = 1): ApiResponseWithPagination<List<ArtistApi>> =
        ApiResponseWithPagination(
            pagination = createPagination(page),
            data = listOf(
                createArtistApi(1),
                createArtistApi(2)
            )
        )
    //-Dao
    fun createSingleArtistDao(id: Int = 1): Artist = createArtist(id)
    fun createArtistListDao(): Flow<List<Artist>> = flow {
        emit(
            listOf<Artist>(
                createArtist(1),
                createArtist(2),
                createArtist(3),
            )
        )
    }

    //ImageEntity
    //Api-Dao
    fun createSingleImageEntity(id: Int = 1): ImageEntity = createImageEntity(id)




    fun createSearchingApi(page: Int = 1): ApiResponseWithPagination<List<SearchingApi>> {
        return ApiResponseWithPagination(
            pagination = createPagination(2),
            data = listOf(
                SearchingApi(
                    id = 1,
                    title = "name1",
                    api_link = "next_url"
                ),
                SearchingApi(
                    id = 2,
                    title = "name2",
                    api_link = "next_url"
                )
            )
        )
    }



    private fun createPagination(page: Int = 1): Pagination {
        return Pagination(
            total = 10,
            limit = 10,
            offset = 10,
            total_pages = 10,
            current_page = page,
            next_url = "next_url_${page + 1}"
        )
    }

    private fun createArtworkApi(id: Int = 1): ArtworkApi {
        return ArtworkApi(
            id = id,
            title = "Title_$id",
            description = "Description_$id",
            image_id = "$id",
            artist_id = id,
            artist_title = "Name_$id"
        )
    }

    private fun createArtistApi(id: Int = 1): ArtistApi {
        return ArtistApi(
            id = id,
            title = "Title_$id",
            description = "Description_$id",
        )
    }

    private fun createArtwork(id: Int= 1, isFavourite: Boolean = false): Artwork {
        return Artwork(
            id = id,
            title = "Artwork_Name_$id",
            description = "Artwork_Description_$id",
            artistId = id,
            artistName = "Artist_Name_$id",
            imageId = "$id",
            isFavourite = isFavourite
        )
    }

    private fun createArtist(id: Int = 1): Artist {
        return Artist(
            id = id,
            name = "Name_$id",
            description = "Description_$id",
        )
    }

    private fun createImageEntity(id: Int = 1): ImageEntity {
        return ImageEntity(
            id = id.toString(),
            artworkId = id,
            imageData = createBitmap(100, 100))
    }
}