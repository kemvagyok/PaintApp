package com.example.painting.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.painting.data.entities.Artist
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artist: Artist)

    @Query("SELECT * FROM artists")
    fun getArtistsAll(): Flow<List<Artist>>

    @Delete
    fun deleteArtist(artist: Artist)

    @Query("SELECT * FROM artists WHERE id = :id")
    suspend fun getArtistById(id: Int): Artist?
}