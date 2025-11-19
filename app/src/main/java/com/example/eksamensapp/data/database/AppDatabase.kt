package com.example.eksamensapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AnimeEntity::class, UserIdeaEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao() : AnimeDao
    abstract fun userIdeaDao() : UserIdeaDao
}