package com.example.eksamensapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE AnimeEntity ADD COLUMN type TEXT NOT NULL DEFAULT ''")
    }
}
@Database(
    entities = [AnimeEntity::class, UserIdeaEntity::class],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao() : AnimeDao
    abstract fun userIdeaDao() : UserIdeaDao
}