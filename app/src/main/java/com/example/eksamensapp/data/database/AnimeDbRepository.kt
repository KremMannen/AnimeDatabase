package com.example.eksamensapp.data.database

import android.content.Context
import androidx.room.Room
import java.lang.Exception
import android.util.Log
import kotlin.jvm.java


object AnimeDbRepository {
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

    suspend fun getCars(): List<Anime> {
        return try {
            _animeDao.getAnime()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getAnimeById(id: Int): Anime? {
        return try {
            _animeDao.getAnimeById(id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun insertAnime(anime: Anime) : Long {
        return try {
            _animeDao.insertAnime(anime)
        } catch (e: Exception) {
            Log.e("AnimeDbRepository", "Error inserting anime", e)
            -1L
        }
    }
}