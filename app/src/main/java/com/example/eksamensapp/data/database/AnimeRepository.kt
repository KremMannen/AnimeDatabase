package com.example.eksamensapp.data.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.eksamensapp.data.api.Anime
import com.example.eksamensapp.data.api.ApiModule
import java.sql.SQLException

object AnimeRepository {

    private lateinit var _appDatabase: AppDatabase
    private val _animeDao by lazy { _appDatabase.animeDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "anime-database"
        )
            .build()
    }

    suspend fun populateDatabaseFromApi() {
        // Sjekk om databasen har data.
        val existingCount = _animeDao.getCount()
        if (existingCount > 0) {
            Log.d("DB_POPULATE", "Database already populated. Skipping API load.")
            return
        }

        // Om den er tom, fylles databasen med data fra API.
        val apiResults = ApiModule.getAllAnime()
        if (apiResults.isEmpty()) {
            Log.e("DB_POPULATE", "API returned empty list. Aborting DB population.")
            return
        }
        val animeEntities = mapToEntityList(apiResults)
        _animeDao.insertAll(animeEntities)
    }
    suspend fun getAllAnime(): List<AnimeEntity> {
        try {
            return _animeDao.getAnime()
        } catch (e: java.lang.Exception) {
            Log.d("AnimeRepository getAllAnime", e.toString())
            return emptyList()
        } catch (e: SQLException) {
            Log.e("AnimeRepository getAllAnime", e.toString())
            return emptyList()
        }
    }
    suspend fun getAnimeById(id: Int): AnimeEntity? {
        try {
            // Sjekk database først
            var animeEntity = _animeDao.getAnimeById(id)
            if (animeEntity != null) {
                Log.i("getAnimeById", "Fant anime i DB med id: ${id}")
                return animeEntity
            }

            // Om databasen har ingen treff, forsøk å hent fra API og lagre dette til database
            searchApiAnimeByIdAndSave(id)

            // Sjekker databasen igjen
            animeEntity = _animeDao.getAnimeById(id)
            if (animeEntity != null) {
                Log.i("getAnimeById", "Fant anime i DB etter API henting med id: ${id}")
                return animeEntity
            }
            return null
        } catch (e: SQLException) {
            Log.e("AnimeRepository getAnimeById", e.toString())
            throw e
        } catch (e: Exception) {
            Log.d("AnimeRepository getAnimeById", e.toString())
            throw e
        }
    }

    suspend fun getAnimeByTitle(title: String): List<AnimeEntity> {
        try {
            // Check DB first
            var animeEntityList = _animeDao.getAnimeByTitle(title)
            if (!animeEntityList.isEmpty()) {
                Log.i("getAnimeByTitle", "Fant anime i DB")
                return animeEntityList
            }
            // Check API, and save to database if found
            searchApiAnimeByTitleAndSave(title)

            // Check DB again
            animeEntityList = _animeDao.getAnimeByTitle(title)
            if (!animeEntityList.isEmpty()) {
                return animeEntityList
            }
            return emptyList()
        } catch (e: SQLException) {
            Log.e("AnimeRepository getAnimeByTitle", e.toString())
            throw e
        } catch (e: Exception) {
            Log.d("AnimeRepository getAnimeByTitle", e.toString())
            throw e
        }
    }

    suspend fun getAnimeByWatchedStatus(): List<AnimeEntity> {
        try {
            return _animeDao.getWatchedAnime()
        } catch (e: java.lang.Exception) {
            Log.d("AnimeRepository getAnimeByWatchedStatus", e.toString())
            return emptyList()
        } catch (e: SQLException) {
            Log.e("AnimeRepository getAnimeByWatchedStatus", e.toString())
            return emptyList()
        }
    }

    suspend fun updateAnime(animeEntity: AnimeEntity) : Int {
        try {
            return _animeDao.updateAnime(animeEntity)
        } catch (e: java.lang.Exception) {
            Log.d("AnimeRepository updateAnime", e.toString())
            return -1
        } catch (e: SQLException) {
            Log.e("AnimeRepository updateAnime", e.toString())
            return -1
        }
    }

    suspend fun searchApiAnimeByIdAndSave(id: Int) {
        try {
            val anime = ApiModule.searchAnimeById(id)

            if (anime != null) {
                val animeEntity = mapToEntity(anime)
                _animeDao.insertAnime(animeEntity)
                Log.i("SearchApiAnimeByIdAndSave", "Lagret anime fra API")
            }

            else {
                Log.i("SearchApiAnimeByIdAndSave", "Fant ikke anime i API")
            }
        } catch (e: SQLException) {
            Log.e("AnimeRepository searchApiAnimeByIdAndSave", e.toString())
        } catch (e: Exception) {
            Log.d("AnimeRepository searchApiAnimeByIdAndSave", e.toString())
        }
    }

    suspend fun searchApiAnimeByTitleAndSave(title: String) {
        try {
            val animeList = ApiModule.searchAnimeByTitle(title)

            if (animeList.isNotEmpty()) {
                Log.i("AnimeRepository SearchAnimeByTitleAndSave", "Liste fra API er ikke tom, forsøker å mappe")
                val animeEntities = mapToEntityList(animeList)

                _animeDao.insertAll(animeEntities)
                Log.i("AnimeRepository SearchAnimeByTitleAndSave", "Lagret anime-liste fra API")
            }

            else {
                Log.i("SearchApiAnimeByTitleAndSave", "Fant ikke anime i API")
            }
        } catch (e: SQLException) {
            Log.e("AnimeRepository searchApiAnimeByTitleAndSave", e.toString())
            throw e
        } catch (e: Exception) {
            Log.d("AnimeRepository searchApiAnimeByTitleAndSave", e.toString())
            throw e
        }
    }
    private fun mapToEntity(anime: Anime): AnimeEntity {
        Log.d("AnimeRepository MapToEntity", "=== Starting mapToEntity ===")
        Log.d("AnimeRepository MapToEntity", "mal_id: ${anime.mal_id}")
        Log.d("AnimeRepository MapToEntity", "title: ${anime.title}")
        Log.d("AnimeRepository MapToEntity", "images: ${anime.images}")
        Log.d("AnimeRepository MapToEntity", "images.jpg: ${anime.images.jpg}")
        Log.d("AnimeRepository MapToEntity", "large_image_url: ${anime.images.jpg.large_image_url}")
        Log.d("AnimeRepository MapToEntity", "synopsis: ${anime.synopsis}")
        Log.d("AnimeRepository MapToEntity", "synopsis is null: ${anime.synopsis == null}")
        Log.d("AnimeRepository MapToEntity", "score: ${anime.score}")
        Log.d("AnimeRepository MapToEntity", "year: ${anime.aired?.prop?.from?.year}")
        Log.d("AnimeRepository MapToEntity", "episodes: ${anime.episodes}")
        Log.d("AnimeRepository MapToEntity", "genres: ${anime.genres}")
        Log.d("AnimeRepository MapToEntity", "genres size: ${anime.genres.size}")
        Log.d("AnimeRepository MapToEntity", "Anime type: ${anime.type}")

        return AnimeEntity(
            id = anime.mal_id,
            title = anime.title,
            imageUrl = anime.images.jpg.large_image_url,
            synopsis = anime.synopsis?: "No synopsis available",
            score = anime.score?: 0.0,
            aired = anime.aired?.prop?.from?.year ?: 0,
            episodes = anime.episodes,
            genres = anime.genres.joinToString(", ") { it.name },
            type = anime.type,
            haveWatched = false,
            isFavorite = false
        )
    }

    private fun mapToEntityList(animeList: List<Anime>): List<AnimeEntity> {
        Log.d("AnimeRepository MapToEntityList", "=== Starting mapToEntityList ===")
        Log.d("AnimeRepository MapToEntityList", "animeList size: ${animeList.size}")

        return animeList.mapIndexed { index, anime ->
            Log.d("AnimeRepository MapToEntityList", "Processing anime at index $index")
            try {
                mapToEntity(anime)
            } catch (e: Exception) {
                Log.e("AnimeRepository MapToEntityList", "Error mapping anime at index $index: ${e.message}")
                Log.e("AnimeRepository MapToEntityList", "Problematic anime: $anime")
                return emptyList()
            }
        }
    }
}