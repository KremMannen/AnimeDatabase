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
    suspend fun getAnimeById(id: Int): AnimeEntity?


    @Query("SELECT * FROM AnimeEntity WHERE title LIKE '%' || :title || '%' COLLATE NOCASE")
    suspend fun getAnimeByTitle(title: String): List<AnimeEntity>


    @Query("SELECT * FROM AnimeEntity WHERE haveWatched = 1")
    suspend fun getWatchedAnime(): List<AnimeEntity>


    @Query("SELECT * FROM AnimeEntity WHERE isFavorite = 1")
    suspend fun getFavoriteAnime(): List<AnimeEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(animeEntity: AnimeEntity): Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animeList: List<AnimeEntity>)
    @Query("SELECT COUNT(*) FROM AnimeEntity")
    suspend fun getCount(): Int


    @Update
    suspend fun updateAnime(animeEntity: AnimeEntity) : Int
}
