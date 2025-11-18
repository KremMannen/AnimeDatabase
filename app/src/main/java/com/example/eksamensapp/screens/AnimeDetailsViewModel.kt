package com.example.eksamensapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.data.Car
import com.example.room.data.room.CarDbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeDetailsViewModel : ViewModel() {
    private val _car = MutableStateFlow<Car?>(null)
    val car = _car.asStateFlow()

    fun setCar(carId: Int) {
        viewModelScope.launch {
            _car.value = CarDbRepository.getCarById(carId)
        }
    }
}