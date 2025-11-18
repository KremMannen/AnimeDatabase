package com.example.eksamensapp.data.database

import android.content.Context
import androidx.room.Room
import com.example.room.data.Car
import java.lang.Exception
import android.util.Log
import kotlin.jvm.java


object CarDbRepository {
    private lateinit var _appDatabase: AppDatabase
    private val _carDao by lazy { _appDatabase.carDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "car-database"
        )
            .build()
    }

    suspend fun getCars(): List<Car> {
        return try {
            _carDao.getCars()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getCarById(id: Int): Car? {
        return try {
            _carDao.getCarById(id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun insertCar(car: Car) : Long {
        return try {
            _carDao.insertCar(car)
        } catch (e: Exception) {
            Log.e("CarDbRepository", "Error inserting car", e)
            -1L
        }
    }
}