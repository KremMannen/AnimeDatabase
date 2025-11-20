package com.example.eksamensapp.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.eksamensapp.components.AnimeListItem
import com.example.eksamensapp.components.AppHeader
import com.example.eksamensapp.navigation.NavigationRoutes
import com.example.eksamensapp.screens.useridea.AnimeUserIdeaViewModel

@Composable
fun AnimeHomeScreen(
    animeHomeViewModel: AnimeHomeViewModel,
    navController : NavController
){
    val animes = animeHomeViewModel.animes.collectAsState()

    Column() {
        AppHeader("Anime Database")
        Text("Home")
        LazyColumn {
            items(animes.value) { anime ->
                AnimeListItem(
                    anime,
                    seeDetails = {
                        navController.navigate(
                            NavigationRoutes.AnimeDetailsRoute(
                                anime.id
                            )
                        )
                    }
                )
            }
        }
    }
}