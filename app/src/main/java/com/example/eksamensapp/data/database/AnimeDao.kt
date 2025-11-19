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

    @Query("DELETE FROM AnimeEntity WHERE id= :id")
    suspend fun deleteAnimeById(id: Int)
    @Update
    suspend fun updateAnime(animeEntity: AnimeEntity) : Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(animeEntity: AnimeEntity): Long
}
