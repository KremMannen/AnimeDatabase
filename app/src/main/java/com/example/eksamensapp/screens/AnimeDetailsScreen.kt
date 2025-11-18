package com.example.eksamensapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.room.components.CarDetailsItem
import kotlin.let

@Composable
fun CarDetailsScreen(
    carDetailsViewModel: AnimeDetailsViewModel,
    navController: NavController,
    carId: Int
){
    val car = carDetailsViewModel.car.collectAsState()

    LaunchedEffect(Unit) {
        carDetailsViewModel.setCar(carId)
    }

    Column {
        Text("Car details")

        car.value?.let {
            CarDetailsItem(
                it,
                goBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}