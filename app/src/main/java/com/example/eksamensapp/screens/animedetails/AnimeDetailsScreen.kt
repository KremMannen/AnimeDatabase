package com.example.eksamensapp.screens.animedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eksamensapp.components.AnimeDetailsItem
import com.example.eksamensapp.components.AppHeader
import com.example.eksamensapp.screens.animedetails.AnimeDetailsViewModel

@Composable
fun AnimeDetailsScreen(
    animeDetailsViewModel: AnimeDetailsViewModel,
    navController: NavController,
    id: Int
) {
    val anime = animeDetailsViewModel.anime.collectAsState()

    LaunchedEffect(Unit) {
        animeDetailsViewModel.setAnime(id)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var title = anime.value?.title ?: ""

        if (title.isEmpty()) {
            title = "No Title Found"
        }

        AppHeader(title.uppercase())


        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {

            item {
                anime.value?.let {
                    AnimeDetailsItem(
                        animeDetailsViewModel = animeDetailsViewModel,
                        goBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}