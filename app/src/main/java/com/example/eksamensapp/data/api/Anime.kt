package com.example.eksamensapp.data.api

data class Anime(
    val mal_id: Int,
    val title: String,
    val images: Images,
    val synopsis: String,
    val score: Double,
    val year: Int,
    val episodes: Int,
    val genres: List<Genre>
)

data class Images(
    val jpg: ImageUrl,
    val webp: ImageUrl?
)

data class ImageUrl(
    val image_url: String,
    val small_image_url: String?,
    val large_image_url: String?
)

data class Genre(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
)