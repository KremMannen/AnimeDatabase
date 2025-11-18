package com.example.eksamensapp.data.api

data class Anime (
    val mal_Id: Int,
    val title: String,
    val imageUrl: String,
    val trailerUrl: String?,
    val synopsis: String?,
    val score: Double?,
    val year: Int?,
    val episodes: Int?
)