package com.example.eksamensapp.data.api

import com.example.eksamensapp.data.database.AnimeDao
import com.example.eksamensapp.data.database.AnimeEntity
import com.example.eksamensapp.data.database.AppDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object AnimeRepository {

    private lateinit var _appDatabase: AppDatabase

    private val _animeDao by lazy { _appDatabase.animeDao() }
    private val _animeService = ApiModule.animeService


    suspend fun getAnimeByIdAndSave(id: Int): AnimeEntity? {
        try {
            val existingAnime = _animeDao.getAnimeById(id)
            if (existingAnime != null) {
                return existingAnime
            }

            val response = _animeService.getAnimeByID(id)
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
                        genres = anime.genres.joinToString {", "},
                        haveWatched = false,
                        isFavorite = false
                    )
                    _animeDao.insertAnime(animeEntity)
                    return animeEntity
                }
            }
            return null
        } catch (e: Exception) {
            return null
        }
    }


    suspend fun searchAnimeByTitle(title: String): List<AnimeEntity> {
        return try {
            val localResults = _animeDao.getAnimeByTitle(title)
            if (localResults != null) {
                return localResults
            }

            val response = _animeService.getAnimeByTitle(title)
            if (response.isSuccessful) {
                response.body()?.let { animeList ->
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
                    animeDao.insertAnimes(animeEntities)
                    return animeEntities
                }
            }
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}