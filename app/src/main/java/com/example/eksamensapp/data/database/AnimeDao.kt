package com.example.eksamensapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao // Data Access Object
interface AnimeDao {
    @Query("SELECT * FROM AnimeEntity")
    suspend fun getAnime(): List<AnimeEntity>

    @Query("SELECT * FROM AnimeEntity WHERE id = :id")
    suspend fun getAnimeById(id: Int): AnimeEntity

    @Query("SELECT * FROM AnimeEntity WHERE title= :title")
    suspend fun getAnimeByTitle(title: String) : AnimeEntity?
}
