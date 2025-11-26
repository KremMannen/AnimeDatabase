package com.example.eksamensapp.data.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiModule {
    private val _httpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()
    private val retroFit = Retrofit.Builder()
        .client(_httpClient)
        .baseUrl("https://api.jikan.moe/v4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val animeService: AnimeService = retroFit.create(AnimeService::class.java)
    suspend fun searchAnimeById(id: Int): Anime? {
        return try {
            val response = animeService.getAnimeByID(id)
            if (response.isSuccessful) {
                val apiResponse = response.body()?.data
                if (apiResponse != null) {
                    apiResponse
                } else {
                    Log.w("SearchAnimeById", "AnimeResponse object is null")
                    null
                }
            } else {
                Log.w("SearchAnimeById", "Response not successful: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.d("SearchAnimeById", "Exception occurred: ${e.message}")
            throw e
        }
    }
    suspend fun searchAnimeByTitle(title: String): List<Anime> {
        return try {
            val response = animeService.getAnimeByTitle(title)
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else emptyList()
        } catch (e: Exception) {
            Log.d("SearchAnimeByTitleCatch", e.toString())
            throw e
        }
    }
    suspend fun getAllAnime() : List<Anime> {
        val allAnime = mutableListOf<Anime>()
        try {
            // Vi henter bare 2 sider for å unngå rate-limiting fra API
            val responsePage1 = animeService.getAnimeByPage(1)
            if (responsePage1.isSuccessful) {
                responsePage1.body()?.data?.let { allAnime.addAll(it) }
            }

            val responsePage2 = animeService.getAnimeByPage(2)
            if (responsePage2.isSuccessful) {
                responsePage2.body()?.data?.let { allAnime.addAll(it) }
            }
        } catch (e: Exception) {
            Log.d("SearchAnimeByTitleCatch", e.toString())
        }
        return allAnime
    }
}