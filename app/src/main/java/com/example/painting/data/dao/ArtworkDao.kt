package com.example.painting.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.painting.data.entities.Artwork
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtworkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtwork(artwork: Artwork)

    @Query("SELECT * FROM artworks")
    fun getArtworksAll(): Flow<List<Artwork>>

    @Delete
    fun deleteArtwork(artwork: Artwork)

    @Query("SELECT * FROM artworks WHERE id = :id")
    suspend fun getArtworkById(id: Int): Artwork?
}