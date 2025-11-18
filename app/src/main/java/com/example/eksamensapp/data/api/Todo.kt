package com.example.eksamensapp.data.api

data class Todo (
    val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int
)