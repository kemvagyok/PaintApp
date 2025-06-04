package com.example.painting

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.painting.data.AppDatabase
import com.example.painting.data.dummy.Data
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest {
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()




    }

    @Test
    fun artworkInsertAddAndDeleteTest() {

        runTest {
            val artwork = Data.createSingleArtworkDao()
            db.artworkDao().insertArtwork(artwork)
            assertEquals(artwork.id, db.artworkDao().getArtworkById(artwork.id)?.id)


            db.artworkDao().deleteArtwork(artwork)
            assertNull(db.artworkDao().getArtworkById(artwork.id))
        }
    }

    @Test
    fun artworkFlowListTest() {
        runTest {
            val artworks = Data.createArtworkListDao().first()
            artworks.forEach { db.artworkDao().insertArtwork(it) }
            val artworksFromDb = db.artworkDao().getArtworksAll().first()
            assertEquals(artworks.size, artworksFromDb.size) }
    }

    @Test
    fun artistInsertAddAndDeleteTest() {
        runTest {
            val artist = Data.createSingleArtistDao()
            db.artistDao().insertArtist(artist)
            assertEquals(artist.id, db.artistDao().getArtistById(artist.id)?.id)

            db.artistDao().deleteArtist(artist)
            assertNull(db.artistDao().getArtistById(artist.id))
        }
    }

    @Test
    fun artistFlowListTest() {
        runTest {
            val artists = Data.createArtistListDao().first()
            artists.forEach { db.artistDao().insertArtist(it) }

            val artistsFromDb = db.artistDao().getArtistsAll().first()
            assertEquals(artists.size, artistsFromDb.size) }
    }

    @After
    fun tearDown() {
        db.close()
    }
}