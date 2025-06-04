package com.example.painting.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String?
)