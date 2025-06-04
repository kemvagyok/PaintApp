package com.example.painting.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.painting.data.AppDatabase
import com.example.painting.data.dao.ArtistDao
import com.example.painting.data.dao.ArtworkDao
import com.example.painting.data.dao.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "painting_database2.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    fun provideArtworkDao(database: AppDatabase): ArtworkDao {
        return database.artworkDao()
    }

    @Provides
    fun provideArtistDao(database: AppDatabase): ArtistDao {
        return database.artistDao()
    }

    @Provides
    fun provideImageDao(database: AppDatabase): ImageDao {
        return database.imageDao()
    }
}
