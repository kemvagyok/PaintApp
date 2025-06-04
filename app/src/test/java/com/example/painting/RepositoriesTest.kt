package com.example.painting

import com.example.painting.data.dao.ArtistDao
import com.example.painting.data.dao.ArtworkDao
import com.example.painting.data.datasource.ArtistRepository
import com.example.painting.data.datasource.ArtistRepositoryImpl
import com.example.painting.data.datasource.ArtworkRepository
import com.example.painting.data.datasource.ArtworkRepositoryImpl
import com.example.painting.data.datasource.ImageRepository
import com.example.painting.data.dummy.Data
import com.example.painting.network.api.ArtistService
import com.example.painting.network.api.ArtworkService
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before




class RepositoriesTest {

    private val keywords : List<String> = listOf("1","2","3")

    private lateinit var artworkRepo: ArtworkRepository
    private lateinit var artistRepo: ArtistRepository
    private lateinit var imageRepo: ImageRepository

    @MockK
    private lateinit var artworkService: ArtworkService

    @MockK
    private lateinit var artworkDao: ArtworkDao

    @Before
    fun setUpArtwork() {
        MockKAnnotations.init(this)
        coEvery {
            artworkService.getArtworkApi(any())
        } returns Data.createSingleArtworkApi(1)

        coEvery {
            artworkService.getArtworksApi()
        } returns Data.createArtworkListApi()

        coEvery {
            artworkService.getArtworksApiSearching(keywords.joinToString(" "))
        } returns Data.createSearchingApi() //Itt különösen nem lényeges, hogy mennyire releváns adatokat adja vissza, hiszen az API-tól függ.


        coEvery {
            artworkDao.getArtworkById(1)
        } returns Data.createSingleArtworkDao(1)

        every {
            artworkDao.getArtworksAll()
        } returns Data.createArtworkListDao()

        coEvery { artworkDao.deleteArtwork(any()) } just Runs

        coEvery { artworkDao.insertArtwork(any()) } just Runs

        artworkRepo = ArtworkRepositoryImpl(
            artworkService = artworkService,
            artworkDao = artworkDao
        )
    }

    @MockK
    private lateinit var artistService: ArtistService

    @MockK
    private lateinit var artistDao: ArtistDao

    @Before
    fun setUpArtist() {
        MockKAnnotations.init(this)

        coEvery {
            artistService.getArtistApi(1)
        } returns Data.createSingleArtistApi(1)

        coEvery {
            artistService.getArtistsApi(1)
        } returns Data.createArtistListApi(1)

        coEvery {
            artistService.getArtistsApiSearching(keywords.joinToString(" "))
        }

        coEvery {
            artistDao.insertArtist(any())
        } just Runs

        coEvery {
            artistDao.deleteArtist(any())
        } just Runs

        coEvery {
            artistDao.getArtistById(1)
        } returns Data.createSingleArtistDao(1)

        coEvery {
            artistDao.getArtistsAll()
        } returns Data.createArtistListDao()

        artistRepo = ArtistRepositoryImpl(
            artistService = artistService,
            artistDao = artistDao
        )
    }

    @Test
    fun testGetArtworkById() = runTest {
        val result = artworkRepo.getArtworkId(1)
        assertNotNull(result)
    }

    @Test
    fun testGetArtworks() = runTest {
        val result = artworkRepo.getArtworks(1)
        assertNotNull(result)
    }

    @Test
    fun testGetArtworksByKeyword() = runTest {
        val result = artworkRepo.getArtworksByKeyword(keywords, 1)
        assertNotNull(result)
    }

    @Test
    fun testGetFavouriteArtworkById() = runTest {
        val result = artworkRepo.getFavouriteArtworkById(1)
        assertNotNull(result)
    }

    @Test
    fun testGetFavoriteArtworksAsFlow() = runTest {
        val result = artworkRepo.getFavoriteArtworksAsFlow()
        assertNotNull(result)
    }

    @Test
    fun testSaveArtworkCallsInsertDao() = runTest {
        val artwork = Data.createSingleArtworkDao(1)
        artworkRepo.saveArtwork(artwork)
        coVerify { artworkDao.insertArtwork(artwork) }
    }

    @Test
    fun testDeleteArtworkCallsDeleteDao() = runTest {
        val artwork = Data.createSingleArtworkDao(1)
        artworkRepo.deleteArtwork(artwork)
        coVerify { artworkDao.deleteArtwork(artwork) }
    }

    @Test
    fun testGetArtistById() = runTest {
        val result = artistRepo.getArtistById(1)
        assertNotNull(result)
    }

    @Test
    fun testGetArtists() = runTest {
        val result = artistRepo.getArtists(1)
        assertNotNull(result)
    }

    @Test
    fun testSaveArtistCallsInsertDao() = runTest {
        val artist = Data.createSingleArtistDao(1)
        artistRepo.saveArtist(artist)
        coVerify { artistDao.insertArtist(artist) }
    }

    @Test
    fun testDeleteArtistCallsDeleteDao() = runTest {
        val artist = Data.createSingleArtistDao(1)
        artistRepo.deleteArtist(artist)
        coVerify { artistDao.deleteArtist(artist) }
    }
}