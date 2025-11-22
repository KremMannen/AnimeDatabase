package com.example.eksamensapp.screens.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.eksamensapp.components.AnimeSearchThumbnail
import com.example.eksamensapp.components.AppHeader
import com.example.eksamensapp.ui.theme.DarkRedHeaderColor
import com.example.eksamensapp.ui.theme.LightGrayBorderColor
import com.example.eksamensapp.ui.theme.SelectedButtonColor

@Composable
fun AnimeSearchScreen(
    animeSearchViewModel: AnimeSearchViewModel,
) {
    val results by animeSearchViewModel.searchedAnime.collectAsState()

    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppHeader("Søk etter Anime")

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
                },
                label = { Text("Søk etter ID eller Tittel", color = Color.White) },
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            )

            // Search button
            Button(
                onClick = {
                    animeSearchViewModel.setSearchedAnime(searchText)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkRedHeaderColor,
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, SelectedButtonColor),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp)
            ) {
                Text("Søk")
            }


            HorizontalDivider(
                thickness = 3.dp,
                color = LightGrayBorderColor
            )

            if (results.isEmpty()) {
                // Display a message when there are no results
                Text(
                    text = "No results found.",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
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
}