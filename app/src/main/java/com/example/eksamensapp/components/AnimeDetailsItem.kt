package com.example.eksamensapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.eksamensapp.data.database.AnimeEntity

@Composable
fun AnimeDetailsItem(
    animeEntity: AnimeEntity,
    goBack: ( () -> Unit )? = null
)
{
    val (mal_id, title, imageUrl, synopsis, score, year, episodes) = animeEntity

    Column {

        Text(title)
        Text(year.toString())
        Text(score.toString())
        Text("Episodes: $episodes")


        if (goBack != null) {
            Button(
                onClick = goBack
            ) {
                Text("Tilbake")
            }
        }
    }
}