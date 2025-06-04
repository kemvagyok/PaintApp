package com.example.painting.network.api

import com.example.painting.network.model.ApiResponseWithPagination
import com.example.painting.network.model.ArtworkApi
import com.example.painting.network.model.SearchingApi
import com.example.painting.network.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtworkService {
    @GET("artworks")
    suspend fun getArtworksApi(@Query("page") page: Int = 1): ApiResponseWithPagination<List<ArtworkApi>>
    @GET("artworks/{id}")
    suspend fun getArtworkApi(@Path("id") id: Int): ApiResponse<ArtworkApi>
    @GET("artworks/search")
    suspend fun getArtworksApiSearching(
        @Query("q") keywords: String,
        @Query("page") page: Int = 1
    ): ApiResponseWithPagination<List<SearchingApi>>
}