package com.example.eksamensapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {

    @GET("anime")
    suspend fun getAllAnime(): Response<AnimeList>

    @GET("todos/user/{id}")
    suspend fun getFilteredTodos(
        @Path("id") id: Int
    ): Response<AnimeList>
}
