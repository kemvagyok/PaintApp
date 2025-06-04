package com.example.painting.data.di

import com.example.painting.data.dao.ArtistDao
import com.example.painting.data.dao.ArtworkDao
import com.example.painting.data.dao.ImageDao
import com.example.painting.data.datasource.ArtistRepository
import com.example.painting.data.datasource.ArtistRepositoryImpl
import com.example.painting.data.datasource.ArtworkRepository
import com.example.painting.data.datasource.ArtworkRepositoryImpl
import com.example.painting.data.datasource.ImageRepository
import com.example.painting.data.datasource.ImageRepositoryImpl
import com.example.painting.network.api.ArtistService
import com.example.painting.network.api.ArtworkService
import com.example.painting.network.api.ImageService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.artic.edu/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideArtworkApiService(retrofit: Retrofit): ArtworkService =
        retrofit.create(ArtworkService::class.java)

    @Provides
    @Singleton
    fun provideArtworkRepository(artworkService: ArtworkService, artworkDao: ArtworkDao): ArtworkRepository =
        ArtworkRepositoryImpl(artworkService, artworkDao)

    @Provides
    @Singleton
    fun provideArtistApiService(retrofit: Retrofit): ArtistService =
        retrofit.create(ArtistService::class.java)

    @Provides
    @Singleton
    fun provideArtistRepository(artistService: ArtistService, artistDao: ArtistDao): ArtistRepository =
        ArtistRepositoryImpl(artistService, artistDao)

    @Provides
    @Singleton
    fun provideImageApiService(retrofit: Retrofit): ImageService =
        retrofit.create(ImageService::class.java)

    @Provides
    @Singleton
    fun provideImageRepository(imageService: ImageService, imageDao: ImageDao): ImageRepository =
        ImageRepositoryImpl(imageService, imageDao)
}
