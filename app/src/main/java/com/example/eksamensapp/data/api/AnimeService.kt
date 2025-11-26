package com.example.eksamensapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeService {

    @GET("anime/{id}")
    suspend fun getAnimeByID(
        @Path("id") id: Int
    ): Response<AnimeResponse>

    @GET("anime")
    suspend fun getAnimeByTitle(
        @Query("q") title: String,
        @Query("sfw") sfw: Boolean = true, // APIet har mye lugubert innhold, denne er viktig for å skåne sensor
        @Query("limit") limit: Int = 5, // For å unngå rate limiting ved mye søk
        @Query("page") page: Int = 1
    ): Response<AnimeListResponse>


    @GET("anime")
    suspend fun getAnimeByPage(
        @Query("page") page: Int
    ): Response<AnimeListResponse>
}
