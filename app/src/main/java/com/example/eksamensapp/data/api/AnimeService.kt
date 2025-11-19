package com.example.eksamensapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {

    @GET("anime")
    suspend fun getAllAnime(): Response<AnimeList>

    @GET("anime/{id}")
    suspend fun getAnimeByID(
        @Path("id") id: Int
    ): Response<Anime>

    @GET("anime?q={title}")
    suspend fun getAnimeByTitle(
        @Path("title") title: String
    ): Response<Anime>
}
