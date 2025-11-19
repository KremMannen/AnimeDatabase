package com.example.eksamensapp.data.api

import android.util.Log
import com.example.eksamensapp.data.database.AnimeEntity
import com.example.eksamensapp.data.database.AppDatabase
import java.sql.SQLException

object AnimeRepository {

    private lateinit var _appDatabase: AppDatabase

    private val _animeDao by lazy { _appDatabase.animeDao() }

    suspend fun getAnimeByIdAndSave(id: Int): AnimeEntity? {
        try {
            val existingAnime = _animeDao.getAnimeById(id)
            if (existingAnime != null) {
                return existingAnime
            }

            val animeEntity = ApiModule.searchAnimeById(id)

            if (animeEntity != null) {
                _animeDao.insertAnime(animeEntity)
                return animeEntity
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
    suspend fun searchAnimeByTitleAndSave(title: String): List<AnimeEntity> {
        return try {
            val localResults = _animeDao.getAnimeByTitle(title)
            if (localResults != null) {
                return localResults
            }

            val animeEntities = ApiModule.searchAnimeByTitle(title)

            if (animeEntities.isNotEmpty()) {
                _animeDao.insertAll(animeEntities)
                return animeEntities
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