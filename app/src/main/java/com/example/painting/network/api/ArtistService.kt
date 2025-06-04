package com.example.painting.network.api

import com.example.painting.network.model.ApiResponseWithPagination
import com.example.painting.network.model.ArtistApi
import com.example.painting.network.model.SearchingApi
import com.example.painting.network.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistService {
    @GET("agents")
    suspend fun getArtistsApi(@Query("page") page: Int = 1): ApiResponseWithPagination<List<ArtistApi>>
    @GET("agents/{id}")
    suspend fun getArtistApi(@Path("id") id: Int,  @Query("page") page: Int = 1): ApiResponse<ArtistApi>
    @GET("agents/search")
    suspend fun getArtistsApiSearching(
        @Query("q") keywords: String,
        @Query("page") page: Int = 1
    ): ApiResponseWithPagination<List<SearchingApi>>

}