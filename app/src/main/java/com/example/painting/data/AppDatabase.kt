package com.example.painting.data

import androidx.room.Database
import com.example.painting.data.dao.ArtistDao
import com.example.painting.data.dao.ArtworkDao
import com.example.painting.data.entities.Artist
import com.example.painting.data.entities.Artwork
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.painting.data.dao.ImageDao
import com.example.painting.data.entities.ImageEntity

@Database(
    entities = [Artist::class, Artwork::class, ImageEntity::class],
    version = 2
)
@TypeConverters(BitmapConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
    abstract fun artworkDao(): ArtworkDao
    abstract fun imageDao(): ImageDao
}