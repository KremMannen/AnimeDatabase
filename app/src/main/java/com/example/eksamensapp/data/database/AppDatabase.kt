package com.example.eksamensapp.data.database

import androidx.room.Database
import com.example.room.data.Car

@Database(
    entities = [Car::class],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun carDao() : AnimeDao
}