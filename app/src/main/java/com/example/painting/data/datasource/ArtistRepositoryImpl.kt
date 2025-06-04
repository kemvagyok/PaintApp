package com.example.painting.data.datasource

import com.example.painting.data.dao.ArtistDao
import com.example.painting.data.entities.Artist
import com.example.painting.data.mapper.toEntity
import com.example.painting.network.api.ArtistService
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val artistService : ArtistService,
    private val artistDao: ArtistDao
) : ArtistRepository {
    override suspend fun getArtists(page: Int): List<Artist> {
        return artistService.getArtistsApi(page = page).
        data.map {
            it.toEntity()
        }
    }

    override suspend fun getArtistsNameByKeyword(keywords: String, page: Int): List<String> {
        val names : MutableList<String> = mutableListOf()
        artistService.getArtistsApiSearching(keywords = keywords, page = page).data.forEach {
            names.add(it.title)
        }
        return names
    }

    override suspend fun getArtistById(id: Int): Artist {
        return artistService.getArtistApi(id).data.toEntity()
    }

    override suspend fun saveArtist(artist: Artist) {
        artistDao.insertArtist(artist)
    }

    override suspend fun deleteArtist(artist: Artist) {
        artistDao.deleteArtist(artist)
    }
}