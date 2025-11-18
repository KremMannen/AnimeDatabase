package com.example.eksamensapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoutes {

    @Serializable
    object Home : NavigationRoutes()

    @Serializable
    object Cars : NavigationRoutes()

    @Serializable
    data class CarsDetailsRoute(val carId: Int) : NavigationRoutes()


}