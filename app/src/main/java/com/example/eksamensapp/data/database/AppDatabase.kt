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

// Denne migreringen ble gjort med hjelp av AI, da SQLite versjonen som er inkludert ikke støtter DROP COLUMN spørring.
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Step 1: Create a new temporary table with the final, correct schema
        // This schema includes the new 'aired' column but excludes the old 'year' column.
        db.execSQL("""
            CREATE TABLE AnimeEntity_new (
                mal_id INTEGER PRIMARY KEY NOT NULL,
                title TEXT,
                image_url TEXT,
                episodes INTEGER,
                score REAL,
                rank INTEGER,
                popularity INTEGER,
                synopsis TEXT,
                background TEXT,
                type TEXT NOT NULL DEFAULT '',
                aired TEXT NOT NULL DEFAULT '' 
            )
        """)

        // Step 2: Copy all data from the old table into the new one.
        // Notice that 'year' is not in the list, as it's being removed.
        db.execSQL("""
            INSERT INTO AnimeEntity_new (mal_id, title, image_url, episodes, score, rank, popularity, synopsis, background, type)
            SELECT mal_id, title, image_url, episodes, score, rank, popularity, synopsis, background, type
            FROM AnimeEntity
        """)

        // Step 3: Delete the old, outdated table
        db.execSQL("DROP TABLE AnimeEntity")

        // Step 4: Rename the new table to the original name
        db.execSQL("ALTER TABLE AnimeEntity_new RENAME TO AnimeEntity")
    }
}


@Database(
    entities = [AnimeEntity::class, UserIdeaEntity::class],
    version = 3,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao() : AnimeDao
    abstract fun userIdeaDao() : UserIdeaDao
}