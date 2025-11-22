package com.example.eksamensapp.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.eksamensapp.components.AnimeSearchThumbnail

@Composable
fun AnimeSearchScreen(
    animeSearchViewModel: AnimeSearchViewModel,
) {
    val results by animeSearchViewModel.searchedAnime.collectAsState()

    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Søkefelt
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                animeSearchViewModel.setSearchedAnime(it)
            },
            label = { Text("Search by ID", color = Color.White) },
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(80.dp))

        if (results.isEmpty()) {
            // Display a message when there are no results
            Text(
                text = "No results found.",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(results.chunked(2)) { pair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AnimeSearchThumbnail(anime = pair[0])
                        if (pair.size > 1) {
                            AnimeSearchThumbnail(anime = pair[1])
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}