package com.example.eksamensapp.screens.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eksamensapp.components.AnimeSearchItem
import com.example.eksamensapp.components.AppHeader
import com.example.eksamensapp.navigation.NavigationRoutes
import com.example.eksamensapp.ui.theme.DarkerRed
import com.example.eksamensapp.ui.theme.LightGrayBorderColor
import com.example.eksamensapp.ui.theme.SelectedButtonColor

@Composable
fun AnimeSearchScreen(
    animeSearchViewModel: AnimeSearchViewModel,
    navController: NavController
) {
    val results = animeSearchViewModel.searchedAnime.collectAsState()
    val isLoading = animeSearchViewModel.isLoading.collectAsState()
    val sqlError = animeSearchViewModel.sqlError.collectAsState()
    val apiError = animeSearchViewModel.apiError.collectAsState()

    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        animeSearchViewModel.showAll()
    }

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

            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                label = { Text("Søk etter ID eller Tittel", color = Color.DarkGray) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            )


            Button(
                onClick = {
                    animeSearchViewModel.handleInput(searchText)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkerRed,
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

            when {
                isLoading.value -> {
                    Text(
                        // Teknisk sett vises denne også ved søk i databasen, men database søk er så raske at
                        // bruker ser meldingen kun når den søker i API
                        text = "Ingen treff funnet i database, søker i API...",
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }
                results.value.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Ingen resultater funnet.",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = if (sqlError.value != null) {
                                "Database status: Could not fetch results:\n\n${sqlError.value}"
                            } else {
                                "Database status: OK. Ingen treff i database"
                            },
                            color = if (sqlError.value != null) Color.Red else Color.Green,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = if (apiError.value != null) {
                                "API status: Error\n\n${apiError.value}"
                            } else {
                                "API status: OK. Ingen treff i API"
                            },
                            color = if (apiError.value != null) Color.Red else Color.Green,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
                    ) {
                        items(results.value) { result ->
                            AnimeSearchItem(
                                anime = result,
                                seeDetails = {
                                    navController.navigate(
                                        NavigationRoutes.AnimeDetailsRoute(
                                            result.id
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}