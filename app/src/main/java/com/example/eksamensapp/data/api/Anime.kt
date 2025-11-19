package com.example.eksamensapp.data.api

data class Anime (
    val mal_id: Int,
    val title: String,
    val imageUrl: String,
    val synopsis: String,
    val score: Double,
    val year: Int,
    val episodes: Int,
    val genres: List<String>
)
