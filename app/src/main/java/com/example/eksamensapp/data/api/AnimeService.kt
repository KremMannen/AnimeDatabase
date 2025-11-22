package com.example.eksamensapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeService {

    @GET("anime")
    suspend fun getAllAnime(): Response<AnimeList>

    @GET("anime/{id}")
    suspend fun getAnimeByID(
        @Path("id") id: Int
    ): Response<ApiResponse>

    @GET("anime")
    suspend fun getAnimeByTitle(
        @Query("q") title: String
    ): Response<AnimeList>

    @GET("anime")
    suspend fun getAnimeByPage(
        @Query("page") page: Int
    ): Response<AnimeList>
}
