package com.example.eksamensapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object AnimeRepository {

    private val _httpClient = OkHttpClient.Builder()
        .addInterceptor(
             HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()

    private val _retroFit = Retrofit.Builder()
        .client(_httpClient)
        .baseUrl("https://api.jikan.moe/v4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val _animeService = _retroFit.create(AnimeService::class.java)

    suspend fun getAnime(): List<Anime> {
        try {
            val response = _animeService.getAllAnime()
            return if (response.isSuccessful){
                response.body()?.anime ?: emptyList()
            } else {
                emptyList()
            }

        } catch (e: Exception) {
            return emptyList()
        }
    }
}