package com.example.eksamensapp.screens.animedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.eksamensapp.components.AnimeDetailsItem
import com.example.eksamensapp.components.AppHeader
import kotlin.let

@Composable
fun AnimeDetailsScreen(
    animeDetailsViewModel: AnimeDetailsViewModel,
    navController: NavController,
    id: Int
){
    val anime = animeDetailsViewModel.anime.collectAsState()

    LaunchedEffect(Unit) {
        animeDetailsViewModel.setAnime(id)
    }

    Column {
        var title = anime.value?.title ?: ""

        if (title.isEmpty()) {
            title = "No Title Found"
        }

        AppHeader(title)

        anime.value?.let {
            AnimeDetailsItem(
                it,
                markSeen = {
                    navController.popBackStack()
                }
            )
        }
    }
}