package com.example.eksamensapp.data.api


object TodoRepository {

    private val _apiHandler : ApiHandler = ApiHandler

    suspend fun getAllTodos() : List<Todo> {
        return _apiHandler.getTodos()
    }
}