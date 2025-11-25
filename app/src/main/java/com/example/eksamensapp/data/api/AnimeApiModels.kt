package com.example.eksamensapp.data.api

data class AnimeResponse(
    val data: Anime
)

data class AnimeListResponse (
    val data: List<Anime>
)
data class Anime(
    val mal_id: Int,
    val title: String,
    val images: Images,
    val synopsis: String?,
    val score: Double? = 0.0,
    val year: Int?,
    val episodes: Int,
    val genres: List<Genre>,
    val type: String
)

data class Images(
    val jpg: ImageUrl
)

data class ImageUrl(
    val large_image_url: String
)

data class Genre(
    val name: String,
)