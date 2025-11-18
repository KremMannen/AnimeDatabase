package com.example.eksamensapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao // Data Access Object
interface AnimeDao {
    @Query("SELECT * FROM Anime")
    suspend fun getAnime(): List<Anime>

    @Query("SELECT * FROM Anime WHERE id = :id")
    suspend fun getAnimeById(id: Int): Anime

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(car: Anime): Long
}
