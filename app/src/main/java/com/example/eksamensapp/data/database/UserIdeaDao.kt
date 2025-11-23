package com.example.eksamensapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao // Data Access Object
interface UserIdeaDao {
    @Query("SELECT * FROM UserIdeaEntity")
    suspend fun getIdeas(): List<UserIdeaEntity>

    @Query("SELECT * FROM UserIdeaEntity WHERE id = :id")
    suspend fun getIdeaById(id: Int): UserIdeaEntity?

    @Query("SELECT * FROM UserIdeaEntity WHERE title LIKE '%' || :title || '%' COLLATE NOCASE")
    suspend fun getIdeaByTitle(title: String) : UserIdeaEntity?

    @Query("DELETE FROM UserIdeaEntity WHERE id= :id")
    suspend fun deleteIdeaById(id: Int) : Int

    @Update
    suspend fun updateIdea(userIdeaEntity: UserIdeaEntity) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIdea(userIdeaEntity: UserIdeaEntity): Long
}