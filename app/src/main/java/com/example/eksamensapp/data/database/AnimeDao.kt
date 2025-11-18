package com.example.eksamensapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.room.data.Car

@Dao // Data Access OBject
interface AnimeDao {
    @Query("SELECT * FROM Car")
    suspend fun getCars(): List<Car>

    @Query("SELECT * FROM Car WHERE id = :id")
    suspend fun getCarById(id: Int): Car

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: Car): Long
}
