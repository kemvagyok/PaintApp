package com.example.painting

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.painting.data.AppDatabase
import com.example.painting.data.dummy.Data
import com.example.painting.network.api.ArtistService
import com.example.painting.network.api.ArtworkService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(AndroidJUnit4::class)
class ServiceInstrumentedTest {

    private lateinit var retrofit: Retrofit
    private lateinit var artworkService: ArtworkService
    private lateinit var artistService: ArtistService
    @Before
    fun setUp() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.artic.edu/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient()) // fontos, ha timeout-ot vagy loggingot akarsz
            .build()
        artworkService = retrofit.create(ArtworkService::class.java)
        artistService = retrofit.create(ArtistService::class.java)
}

    @Test
    fun testGetArtworksFromApi() = runBlocking {
        val responseArtworks = artworkService.getArtworksApi()
        val artworks = responseArtworks.data
        assertTrue("API returned empty list!", artworks.isNotEmpty())
    }

    @Test
    fun testGetArtistFromApi() = runBlocking {
        val responseArtists = artistService.getArtistsApi()
        val artists = responseArtists.data
        assertTrue("API returned empty list!", artists.isNotEmpty())
    }

    @Test
    fun testGetArtworksSearchingFromApi() = runBlocking {

        val responseSearchingArtworks = artworkService.getArtworksApiSearching("Cleopatra")
        val artworks = responseSearchingArtworks.data
        assertTrue("API returned empty list!", artworks.isNotEmpty())
    }

    @Test
    fun testGetArtistsSearchingFromApi() = runBlocking {

        val responseSearchingArtists = artistService.getArtistsApiSearching("Giovanna")
        val artists = responseSearchingArtists.data
        assertTrue("API returned empty list!", artists.isNotEmpty())
    }
    //Image-vel kapcsolatos tesztek implementálása viszont nem kerül sorra, mivel többször függésben van (kell hozzá egy artwork, és ennek az image_id-jét kell szerezni).

}