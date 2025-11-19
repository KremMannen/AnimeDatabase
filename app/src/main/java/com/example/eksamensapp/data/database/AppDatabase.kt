package com.example.eksamensapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eksamensapp.data.api.Anime

@Database(
    entities = [Anime::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao() : AnimeDao
}