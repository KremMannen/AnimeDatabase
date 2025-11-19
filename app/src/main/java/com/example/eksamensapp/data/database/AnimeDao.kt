package com.example.eksamensapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao // Data Access Object
interface AnimeDao {
    @Query("SELECT * FROM Anime")
    suspend fun getAnime(): List<Anime>

    @Query("SELECT * FROM Anime WHERE id = :id")
    suspend fun getAnimeById(id: Int): Anime

    @Query("SELECT * FROM Anime WHERE title= :title")
    suspend fun getAnimeByTitle(title: String) : Anime?

    @Query("DELETE FROM Anime WHERE id= :id")
    suspend fun deleteAnimeById(id: Int)

    @Update
    suspend fun updateAnime(anime: Anime) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: Anime): Long
}
