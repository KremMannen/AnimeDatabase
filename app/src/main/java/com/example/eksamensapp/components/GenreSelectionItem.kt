package com.example.eksamensapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.eksamensapp.ui.theme.SelectedButtonColor
import com.example.eksamensapp.ui.theme.TransparentRedBackgroundColor

@Composable
fun GenreSelectionItem(
    selectedGenres: Set<String>,
    onGenresChanged: (Set<String>) -> Unit
){
    val availableGenres = listOf(
        "Action", "Adventure", "Comedy", "Drama", "Fantasy",
        "Horror", "Mystery", "Romance", "Sci-Fi", "Slice of Life"
    )

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
                        val newSelection = if (selectedGenres.contains(genre)) {
                            selectedGenres - genre
                        } else {
                            selectedGenres + genre
                        }
                        onGenresChanged(newSelection)
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
}