package com.example.eksamensapp.screens.useridea.updateidea.editidea

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eksamensapp.components.AppHeaderItem
import com.example.eksamensapp.components.GenreSelectionItem
import com.example.eksamensapp.data.database.UserIdeaEntity
import com.example.eksamensapp.screens.useridea.updateidea.UpdateIdeaViewModel
import com.example.eksamensapp.ui.theme.DarkRed
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

    var titleError by remember { mutableStateOf<String?>(null) }
    var synopsisError by remember { mutableStateOf<String?>(null) }
    var genresError by remember { mutableStateOf<String?>(null) }

    fun validateFields(): Boolean {
        var isValid = true

        if (title.isBlank()) {
            titleError = "Vennligst fyll inn tittel"
            isValid = false
        } else {
            titleError = null
        }

        if (synopsis.isBlank()) {
            synopsisError = "Vennligst fyll inn synopsis"
            isValid = false
        } else {
            synopsisError = null
        }

        if (selectedGenres.isEmpty()) {
            genresError = "Vennligst velg minst én sjanger"
            isValid = false
        } else {
            genresError = null
        }

        return isValid
    }

    fun handleUpdateIdea() {
        if (!validateFields()) {
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
        AppHeaderItem("Anime idèer", onBackClick = { navController.popBackStack() })

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
                label = { Text(
                    titleError ?: "Tittel...",
                    color = if (titleError != null) Color.Red else Color.DarkGray
                )  },
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
                label = { Text(
                    synopsisError ?: "Synopsis...",
                    color = if (synopsisError != null) Color.Red else Color.DarkGray
                )  },
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

            Column {
                GenreSelectionItem(
                    selectedGenres = selectedGenres,
                    onGenresChanged = { newGenres ->
                        selectedGenres = newGenres
                        if (newGenres.isNotEmpty()) genresError = null
                    }
                )

                if (genresError != null) {
                    Text(
                        text = genresError!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                    )
                }
            }

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
                    containerColor = DarkRed
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