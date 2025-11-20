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
import coil.compose.rememberImagePainter
import com.example.eksamensapp.data.database.AnimeEntity
import com.example.eksamensapp.ui.theme.RedBackgroundColor

@Composable
fun AnimeDetailsItem(
    animeEntity: AnimeEntity,
    markSeen: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(RedBackgroundColor, shape = RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = animeEntity.imageUrl),
                contentDescription = animeEntity.title,
                modifier = Modifier
                    .width(100.dp)
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

                if (markSeen != null) {
                    Button(
                        onClick = markSeen,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Marker som sett")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(RedBackgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = animeEntity.synopsis, color = Color.White)
        }
    }
}