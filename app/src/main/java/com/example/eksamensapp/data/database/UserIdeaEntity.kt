package com.example.eksamensapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserIdeaEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val synopsis: String,
    val genres: String,
)
