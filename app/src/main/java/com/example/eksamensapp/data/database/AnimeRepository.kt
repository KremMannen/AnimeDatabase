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
        // Check if DB already has data
        val existingCount = _animeDao.getCount()
        if (existingCount > 0) {
            Log.d("DB_POPULATE", "Database already populated. Skipping API load.")
            return
        }

        // Populate from API
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
            Log.d("getAllAnime", e.toString())
            return emptyList()
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            return emptyList()
        }
    }
    suspend fun getAnimeById(id: Int): AnimeEntity? {
        try {
            // Check DB first
            var animeEntity = _animeDao.getAnimeById(id)
            if (animeEntity != null) {
                Log.i("getAnimeById", "Fant anime i DB")
                return animeEntity
            }
            // Check API, and save to database if found
            searchApiAnimeByIdAndSave(id)

            // Check DB again
            animeEntity = _animeDao.getAnimeById(id)
            if (animeEntity != null) {
                Log.i("getAnimeById", "Fant anime i DB etter API henting")
                return animeEntity
            }
            return null
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            throw e
        } catch (e: Exception) {
            Log.d("getAnimeByTitle", e.toString())
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
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            throw e
        } catch (e: Exception) {
            Log.d("getAnimeByTitle", e.toString())
            throw e
        }
    }

    suspend fun getAnimeByWatchedStatus(): List<AnimeEntity> {
        try {
            return _animeDao.getWatchedAnime()
        } catch (e: java.lang.Exception) {
            Log.d("getAllAnime", e.toString())
            return emptyList()
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            return emptyList()
        }
    }

    suspend fun updateAnime(animeEntity: AnimeEntity) : Int {
        try {
            return _animeDao.updateAnime(animeEntity)
        } catch (e: java.lang.Exception) {
            Log.d("updateAnimeCatch", e.toString())
            return -1
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved oppdatering av data ${e.message}")
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
            Log.e("SQLException", "SQLEx ved oppretting av data ${e.message}")
        } catch (e: Exception) {
            Log.d("SearchApiAnimeByIdAndSave", e.toString())
        }
    }

    suspend fun searchApiAnimeByTitleAndSave(title: String) {
        try {
            val animeList = ApiModule.searchAnimeByTitle(title)

            if (animeList.isNotEmpty()) {
                Log.i("SearchAnimeByTitleAndSave", "Liste fra API er ikke tom, forsøker å mappe")
                val animeEntities = mapToEntityList(animeList)

                _animeDao.insertAll(animeEntities)
                Log.i("SearchAnimeByTitleAndSave", "Lagret anime-liste fra API")
            }

            else {
                Log.i("SearchApiAnimeByTitleAndSave", "Fant ikke anime i API")
            }
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved oppretting av data ${e.message}")
            throw e
        } catch (e: Exception) {
            Log.d("SearchApiAnimeByTitleAndSave", e.toString())
            throw e
        }
    }
    private fun mapToEntity(anime: Anime): AnimeEntity {
        Log.d("MapToEntity", "=== Starting mapToEntity ===")
        Log.d("MapToEntity", "mal_id: ${anime.mal_id}")
        Log.d("MapToEntity", "title: ${anime.title}")
        Log.d("MapToEntity", "images: ${anime.images}")
        Log.d("MapToEntity", "images.jpg: ${anime.images.jpg}")
        Log.d("MapToEntity", "large_image_url: ${anime.images.jpg.large_image_url}")
        Log.d("MapToEntity", "synopsis: ${anime.synopsis}")
        Log.d("MapToEntity", "synopsis is null: ${anime.synopsis == null}")
        Log.d("MapToEntity", "score: ${anime.score}")
        Log.d("MapToEntity", "year: ${anime.year}")
        Log.d("MapToEntity", "episodes: ${anime.episodes}")
        Log.d("MapToEntity", "genres: ${anime.genres}")
        Log.d("MapToEntity", "genres size: ${anime.genres.size}")

        return AnimeEntity(
            id = anime.mal_id,
            title = anime.title,
            imageUrl = anime.images.jpg.large_image_url,
            synopsis = anime.synopsis?: "No synopsis available",
            score = anime.score,
            year = anime.year?: 0,
            episodes = anime.episodes,
            genres = anime.genres.joinToString(", ") { it.name },
            haveWatched = false,
            isFavorite = false
        )
    }

    private fun mapToEntityList(animeList: List<Anime>): List<AnimeEntity> {
        Log.d("MapToEntityList", "=== Starting mapToEntityList ===")
        Log.d("MapToEntityList", "animeList size: ${animeList.size}")

        return animeList.mapIndexed { index, anime ->
            Log.d("MapToEntityList", "Processing anime at index $index")
            try {
                mapToEntity(anime)
            } catch (e: Exception) {
                Log.e("MapToEntityList", "Error mapping anime at index $index: ${e.message}")
                Log.e("MapToEntityList", "Problematic anime: $anime")
                throw e
            }
        }
    }
}