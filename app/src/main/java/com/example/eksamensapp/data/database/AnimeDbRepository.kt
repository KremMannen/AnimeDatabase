package com.example.eksamensapp.data.database

import android.content.Context
import androidx.room.Room
import java.lang.Exception
import android.util.Log
import java.sql.SQLException
import kotlin.jvm.java


object AnimeDbRepository {

    private val _animeDao by lazy { _appDatabase.animeDao() }
    private lateinit var _appDatabase: AppDatabase


    suspend fun getAnime(): List<AnimeEntity> {
        return try {
            _animeDao.getAnime()
        } catch (e: Exception) {
            Log.d("getAnimeCatch", e.toString())
            emptyList()
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            return emptyList()
        }
    }

    suspend fun getAnimeById(id: Int): AnimeEntity? {
        return try {
            _animeDao.getAnimeById(id)
        } catch (e: Exception) {
            Log.d("getAnimeByIdCatch", e.toString())
            null
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            return null
        }
    }

    suspend fun getAnimeByTitle(title: String) : AnimeEntity? {
        return try {
            _animeDao.getAnimeByTitle(title)
        } catch (e: Exception) {
            Log.d("getAnimeByTitleCatch", e.toString())
            null
        } catch (e: SQLException) {
            Log.e("SQLException", "SQLEx ved henting av data ${e.message}")
            return null
        }
    }
}