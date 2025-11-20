package com.example.eksamensapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.eksamensapp.data.database.AnimeEntity
import com.example.eksamensapp.screens.animedetails.AnimeDetailsViewModel
import com.example.eksamensapp.ui.theme.TransparentRedBackgroundColor

@Composable
fun AnimeDetailsItem(
    animeEntity: AnimeEntity,
    animeDetailsViewModel: AnimeDetailsViewModel,
    goBack: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(TransparentRedBackgroundColor, shape = RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = animeEntity.imageUrl),
                contentDescription = animeEntity.title,
                modifier = Modifier
                    .height(200.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.FillHeight
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(text = animeEntity.title, color = Color.White)
                Text(text = "Year: ${animeEntity.year}", color = Color.White)
                Text(text = "Episodes: ${animeEntity.episodes}", color = Color.White)
                Text(text = "Genres: ${animeEntity.genres}", color = Color.White)
                Text(text = "Rating: ${animeEntity.score}", color = Color.White)

                Button(
                    onClick = {
                        animeDetailsViewModel.markAsWatched(animeEntity)
                    }
                ) {
                    Text(text = "Merk som sett")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(TransparentRedBackgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = animeEntity.synopsis, color = Color.White)
        }
        if (goBack != null) {
            Button(
                onClick = goBack,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Gå tilbake")
            }
        }
    }
}