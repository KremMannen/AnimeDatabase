package com.example.eksamensapp.data.api

import android.util.Log
import com.example.eksamensapp.data.database.AnimeEntity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.SQLException

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
                response.body()
            } else null
        } catch (e: Exception) {
            Log.d("SearchAnimeById", e.toString())
            null
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
            emptyList()
        }
    }

    suspend fun getAllAnime() : List<Anime> {
        return try {
            val response = animeService.getAllAnime()
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else emptyList()
        } catch (e: Exception) {
            Log.d("SearchAnimeByTitleCatch", e.toString())
            emptyList()
        }
    }

}