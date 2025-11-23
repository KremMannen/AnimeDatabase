package com.example.eksamensapp.screens.useridea.addidea

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
import androidx.navigation.NavController
import com.example.eksamensapp.components.AnimeSearchItem
import com.example.eksamensapp.components.AppHeader
import com.example.eksamensapp.components.DetailsAppHeader
import com.example.eksamensapp.data.database.UserIdeaDbRepository
import com.example.eksamensapp.data.database.UserIdeaEntity
import com.example.eksamensapp.navigation.NavigationRoutes
import com.example.eksamensapp.ui.theme.DarkRedHeaderColor
import com.example.eksamensapp.ui.theme.LightGrayBorderColor
import com.example.eksamensapp.ui.theme.SelectedButtonColor

@Composable
fun AddIdeaScreen(
    addIdeaViewModel: AddIdeaViewModel,
    navController: NavController
) {
    var title by remember { mutableStateOf("") }
    var synopsis by remember { mutableStateOf("") }
    var selectedGenres by remember { mutableStateOf(setOf<String>()) }

    val availableGenres = listOf(
        "Action", "Adventure", "Comedy", "Drama", "Fantasy",
        "Horror", "Mystery", "Romance", "Sci-Fi", "Slice of Life"
    )

    fun handleAddIdea() {
        if (title.isBlank() || synopsis.isBlank() || selectedGenres.isEmpty()) {
            return
        }

        val idea = UserIdeaEntity(
            title = title,
            synopsis = synopsis,
            genres = selectedGenres.joinToString(", ")
        )

        addIdeaViewModel.handleAddIdea(idea)
        title = ""
        synopsis = ""
        selectedGenres = emptySet()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DetailsAppHeader("Legg til Anime Idè", onBackClick = { navController.popBackStack() })


        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = { Text("Tittel...", color = Color.White) },
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            OutlinedTextField(
                value = synopsis,
                onValueChange = {
                    synopsis = it
                },
                label = { Text("Synopsis...", color = Color.White) },
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(120.dp)
            )

            Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                Text("Velg sjangre:", color = Color.White, modifier = Modifier.padding(bottom = 8.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    availableGenres.forEach { genre ->
                        Button(
                            onClick = {
                                selectedGenres = if (selectedGenres.contains(genre)) {
                                    selectedGenres - genre
                                } else {
                                    selectedGenres + genre
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedGenres.contains(genre))
                                    DarkRedHeaderColor else Color.Gray
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(genre)
                        }
                    }
                }
            }

            HorizontalDivider(
                thickness = 3.dp,
                color = LightGrayBorderColor,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Button at bottom
        Button(
            onClick = {
                handleAddIdea()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkRedHeaderColor
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(56.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        ) {
            Text("Legg til Anime Idè")
        }
    }
}