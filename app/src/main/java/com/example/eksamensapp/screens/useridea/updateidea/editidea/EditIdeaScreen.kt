package com.example.eksamensapp.screens.useridea.updateidea.editidea

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.eksamensapp.components.DetailsAppHeader
import com.example.eksamensapp.components.GenreSelectionItem

import com.example.eksamensapp.data.database.UserIdeaEntity

import com.example.eksamensapp.screens.useridea.updateidea.UpdateIdeaViewModel

import com.example.eksamensapp.ui.theme.DarkRedHeaderColor
import com.example.eksamensapp.ui.theme.LightGrayBorderColor

import com.example.eksamensapp.ui.theme.SelectedBorderColor
import com.example.eksamensapp.ui.theme.SelectedButtonColor


@Composable
fun EditIdeaScreen(
    updateIdeaViewModel: UpdateIdeaViewModel,
    navController: NavController,
    id: Int
) {
    var title by remember { mutableStateOf("") }
    var synopsis by remember { mutableStateOf("") }
    var selectedGenres by remember { mutableStateOf(setOf<String>()) }

    fun handleUpdateIdea() {
        if (title.isBlank() || synopsis.isBlank() || selectedGenres.isEmpty() || id == null) {
            // TODO: introduce function that gives user feedback
            return
        }
        val idea = UserIdeaEntity(
            id = id,
            title = title,
            synopsis = synopsis,
            genres = selectedGenres.joinToString(", ")
        )

        updateIdeaViewModel.handleUpdateIdea(idea)
        title = ""
        synopsis = ""
        selectedGenres = emptySet()
        navController.popBackStack()
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





            TextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = { Text("Tittel...", color = Color.DarkGray) },
                textStyle = TextStyle(color = Color.Black),
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
                textStyle = TextStyle(color = Color.DarkGray),
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


            GenreSelectionItem(
                selectedGenres = selectedGenres,
                onGenresChanged = { newGenres ->
                    selectedGenres = newGenres
                }
            )

            HorizontalDivider(
                thickness = 3.dp,
                color = LightGrayBorderColor,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            Button(
                onClick = {
                    handleUpdateIdea()
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
                    text = "Oppdater Idè",
                    fontSize = 20.sp)
            }
        }
    }
}