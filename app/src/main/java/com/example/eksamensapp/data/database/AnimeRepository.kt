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
        if (apiResults.isNullOrEmpty()) {
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
            return _animeDao.getAnimeById(id)
        } catch (e: java.lang.Exception) {
            Log.d("getUserIdeaByIdCatch", e.toString())
            return null
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            return null
        }
    }

    suspend fun getAnimeByIdAndSave(id: Int): AnimeEntity? {
        return try {
            // Check DB first
            _animeDao.getAnimeById(id)?.let {
                return it
            }

            // Check API if no hits in local database
            val anime = ApiModule.searchAnimeById(id)
            if (anime != null) {
                val animeEntity = mapToEntity(anime)
                _animeDao.insertAnime(animeEntity)
                return animeEntity
            }
            null
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved oppretting av data ${e.message}")
            null
        } catch (e: Exception) {
            Log.d("GetAnimeByIdAndSaveCatch", e.toString())
            null
        }
    }

    suspend fun searchAnimeByTitleAndSave(title: String): List<AnimeEntity> {
        return try {
            val localResults = _animeDao.getAnimeByTitle(title)
            if (localResults.isNotEmpty()) {
                return localResults
            }
            val apiResults = ApiModule.searchAnimeByTitle(title)
            if (apiResults.isNotEmpty()) {
                val animeEntities = mapToEntityList(apiResults)
                _animeDao.insertAll(animeEntities)
                return animeEntities
            }
            emptyList()
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved oppretting av data ${e.message}")
            emptyList()
        } catch (e: Exception) {
            Log.d("SearchAnimeByTitleAndSaveCatch", e.toString())
            emptyList()
        }
    }
    private fun mapToEntity(anime: Anime): AnimeEntity {
        return AnimeEntity(
            id = anime.mal_id,
            title = anime.title,
            imageUrl = anime.images.jpg.image_url,
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