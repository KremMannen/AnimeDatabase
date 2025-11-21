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
                return animeEntity
            }
            // Check API, and save to database if found
            searchApiAnimeByIdAndSave(id)

            // Check DB again
            animeEntity = _animeDao.getAnimeById(id)
            if (animeEntity != null) {
                return animeEntity
            }
            return null
        } catch (e: java.lang.Exception) {
            Log.d("getUserIdeaByIdCatch", e.toString())
            return null
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            return null
        }
    }

    suspend fun getAnimeByTitle(title: String): List<AnimeEntity> {
        try {
            // Check DB first
            var animeEntity = _animeDao.getAnimeByTitle(title)
            if (!animeEntity.isEmpty()) {
                return animeEntity
            }
            // Check API, and save to database if found
            searchApiAnimeByTitleAndSave(title)

            // Check DB again
            animeEntity = _animeDao.getAnimeByTitle(title)
            if (!animeEntity.isEmpty()) {
                return animeEntity
            }
            return emptyList()
        } catch (e: java.lang.Exception) {
            Log.d("getUserIdeaByIdCatch", e.toString())
            return emptyList()
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            return emptyList()
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
            val animeEntity = ApiModule.searchAnimeById(id)
            if (animeEntity != null) {
                val animeEntity = mapToEntity(animeEntity)
                _animeDao.insertAnime(animeEntity)
                Log.i("SearchApiAnimeByIdAndSave", "Lagret anime fra API")
            }
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved oppretting av data ${e.message}")
        } catch (e: Exception) {
            Log.d("SearchApiAnimeByIdAndSave", e.toString())
        }
    }

    suspend fun searchApiAnimeByTitleAndSave(title: String) {
        try {
            val apiResults = ApiModule.searchAnimeByTitle(title)
            if (apiResults.isNotEmpty()) {
                val animeEntities = mapToEntityList(apiResults)
                _animeDao.insertAll(animeEntities)
                Log.i("SearchAnimeByTitleAndSave", "Lagret anime-liste fra API")
            }
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved oppretting av data ${e.message}")
        } catch (e: Exception) {
            Log.d("SearchApiAnimeByTitleAndSave", e.toString())
        }
    }
    private fun mapToEntity(anime: Anime): AnimeEntity {
        return AnimeEntity(
            id = anime.mal_id,
            title = anime.title,
            imageUrl = anime.images.jpg.large_image_url,
            synopsis = anime.synopsis,
            score = anime.score,
            year = anime.year,
            episodes = anime.episodes,
            genres = anime.genres.joinToString(", ") { it.name },
            haveWatched = false,
            isFavorite = false
        )
    }
    private fun mapToEntityList(animeList: List<Anime>): List<AnimeEntity> {
        return animeList.map { mapToEntity(it) }
    }
}