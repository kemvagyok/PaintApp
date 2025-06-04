package com.example.painting.network.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ImageService {
    @GET
    suspend fun downloadImage(@Url url: String): Response<ResponseBody>
}