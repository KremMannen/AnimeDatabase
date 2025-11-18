package com.example.eksamensapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TodoService {

    @GET("todos")
    suspend fun getAllTodos(): Response<TodoList>

    @GET("todos/user/{id}")
    suspend fun getFilteredTodos(
        @Path("id") id: Int
    ): Response<TodoList>
}
