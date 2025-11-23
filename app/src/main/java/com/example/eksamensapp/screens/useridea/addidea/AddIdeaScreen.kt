package com.example.eksamensapp.screens.useridea.addidea

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eksamensapp.components.DetailsAppHeader
import com.example.eksamensapp.data.database.UserIdeaEntity
import com.example.eksamensapp.ui.theme.DarkRedHeaderColor
import com.example.eksamensapp.ui.theme.LightGrayBorderColor
import com.example.eksamensapp.ui.theme.SelectedBorderColor
import com.example.eksamensapp.ui.theme.SelectedButtonColor
import com.example.eksamensapp.ui.theme.TransparentRedBackgroundColor

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
        DetailsAppHeader("Anime idèer", onBackClick = { navController.popBackStack() })

        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp),
                border = BorderStroke(1.dp, LightGrayBorderColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkRedHeaderColor),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Opprett eget anime konsept!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Her kan du opprette egne anime konsepter, og lagre dem til en lokal database.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center,

                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = { Text("Tittel...", color = Color.DarkGray) },
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.LightGray,
                    disabledContainerColor = Color.Gray,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = SelectedButtonColor,
                        )
            )

            TextField(
                value = synopsis,
                onValueChange = {
                    synopsis = it
                },
                label = { Text("Synopsis...", color = Color.DarkGray) },
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.DarkGray),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.LightGray,
                    disabledContainerColor = Color.Gray,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(
                        width = 1.dp,
                        color = SelectedButtonColor,
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))


            Column(modifier = Modifier.fillMaxWidth()) {
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
                                    SelectedButtonColor else TransparentRedBackgroundColor
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(genre)
                        }
                    }
                }
            }

            HorizontalDivider(
                thickness = 3.dp,
                color = LightGrayBorderColor,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            Button(
                onClick = {
                    handleAddIdea()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkRedHeaderColor
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, SelectedBorderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Legg til Anime Idè",
                    fontSize = 20.sp)
            }
        }
    }
}