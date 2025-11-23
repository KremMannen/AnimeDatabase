package com.example.eksamensapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoutes {

    @Serializable
    object Home : NavigationRoutes()

    @Serializable
    object Search : NavigationRoutes()

    @Serializable
    object UserIdea : NavigationRoutes()

    @Serializable
    object AddIdea : NavigationRoutes()

    @Serializable
    object Watched : NavigationRoutes()

    @Serializable
    data class AnimeDetailsRoute(val id: Int) : NavigationRoutes()


}