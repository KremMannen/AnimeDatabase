package com.example.eksamensapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Anime (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val imageUrl: String,
    val synopsis: String?,
    val score: Double?,
    val year: Int?,
    val episodes: Int?,
    val haveWatched: Boolean = false,
    val isFavorite: Boolean = false,
    val isFromApi: Boolean = true,
    val userModified: Boolean = false,
    )