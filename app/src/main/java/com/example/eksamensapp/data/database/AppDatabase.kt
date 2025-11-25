package com.example.eksamensapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [AnimeEntity::class, UserIdeaEntity::class],
    version = 3,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao() : AnimeDao
    abstract fun userIdeaDao() : UserIdeaDao
}