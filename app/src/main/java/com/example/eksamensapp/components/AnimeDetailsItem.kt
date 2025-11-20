package com.example.eksamensapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    animeDetailsViewModel: AnimeDetailsViewModel,
    goBack: (() -> Unit)? = null
) {
    val anime = animeDetailsViewModel.anime.collectAsState()
    val currentAnime = anime.value ?: return

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
                painter = rememberAsyncImagePainter(model = currentAnime.imageUrl),
                contentDescription = currentAnime.title,
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
                Text(text = currentAnime.title, color = Color.White)
                Text(text = "Year: ${currentAnime.year}", color = Color.White)
                Text(text = "Episodes: ${currentAnime.episodes}", color = Color.White)
                Text(text = "Genres: ${currentAnime.genres}", color = Color.White)
                Text(text = "Rating: ${currentAnime.score}", color = Color.White)

                Button(
                    onClick = {
                        if (currentAnime.haveWatched) {
                            animeDetailsViewModel.markAsUnwatched(currentAnime)
                        } else {
                            animeDetailsViewModel.markAsWatched(currentAnime)
                        }
                    }
                ) {
                    Text(
                        text = if (currentAnime.haveWatched) "Merk som usett" else "Merk som sett"
                    )
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
            Text(text = currentAnime.synopsis, color = Color.White)
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