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

    suspend fun searchAnimeById(id: Int): AnimeEntity? {
        try {
            val response = animeService.getAnimeByID(id)
            if (response.isSuccessful) {
                response.body()?.let { anime ->
                    val animeEntity = AnimeEntity(
                        id = anime.mal_id,
                        title = anime.title,
                        imageUrl = anime.images.jpg.image_url,
                        synopsis = anime.synopsis,
                        score = anime.score,
                        year = anime.year,
                        episodes = anime.episodes,
                        genres = anime.genres.joinToString (", ") {it.name},
                        haveWatched = false,
                        isFavorite = false
                    )
                    return animeEntity
                }
            }
            return null
        } catch (e: Exception) {
            Log.d("GetAnimeByIdAndSaveCatch", e.toString())
            return null
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved oppretting av data ${e.message}")
            return null
        }
    }

    suspend fun searchAnimeByTitle(title: String): List<AnimeEntity> {
        return try {
            val response = animeService.getAnimeByTitle(title)
            if (response.isSuccessful) {
                response.body()?.data?.let { animeList ->
                    val animeEntities = animeList.map { anime ->
                        AnimeEntity(
                            id = anime.mal_id,
                            title = anime.title,
                            imageUrl = anime.images.jpg.image_url,
                            synopsis = anime.synopsis,
                            score = anime.score,
                            year = anime.year,
                            episodes = anime.episodes,
                            genres = anime.genres.joinToString { it.name },
                            haveWatched = false,
                            isFavorite = false
                        )
                    }
                    return animeEntities
                }
            }
            emptyList()
        } catch (e: Exception) {
            Log.d("SearchAnimeByTitleAndSaveCatch", e.toString())
            emptyList()
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved oppretting av data ${e.message}")
            return emptyList()
        }
    }
}