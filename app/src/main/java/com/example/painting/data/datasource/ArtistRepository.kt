package com.example.painting.data.datasource

import com.example.painting.data.entities.Artist

interface ArtistRepository {
     suspend fun getArtists(page: Int = 1): List<Artist>
     suspend fun getArtistsNameByKeyword(keyword: String, page: Int = 1): List<String>
     suspend fun getArtistById(id : Int): Artist
     suspend fun saveArtist(artist: Artist)
     suspend fun deleteArtist(artist: Artist)
}