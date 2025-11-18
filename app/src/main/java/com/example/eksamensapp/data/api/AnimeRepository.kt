package com.example.eksamensapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object AnimeRepository {

    private val _httpClient = OkHttpClient.Builder()
        .addInterceptor(
             HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()

    private val _retroFit = Retrofit.Builder()
        .client(_httpClient)
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val _todoService = _retroFit.create(AnimeService::class.java)

    suspend fun getTodos(): List<Anime> {
        try {
            val response = _todoService.getAllTodos()
            return if (response.isSuccessful){
                response.body()?.todos ?: emptyList()
            } else {
                emptyList()
            }

        } catch (e: Exception) {
            return emptyList()
        }
    }

}