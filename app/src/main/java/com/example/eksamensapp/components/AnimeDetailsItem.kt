package com.example.eksamensapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.eksamensapp.screens.animedetails.AnimeDetailsViewModel
import com.example.eksamensapp.ui.theme.DarkerRed
import com.example.eksamensapp.ui.theme.LightGrayBorderColor
import com.example.eksamensapp.ui.theme.RedBackgroundColor
import com.example.eksamensapp.ui.theme.SelectedBorderColor
import com.example.eksamensapp.ui.theme.SelectedButtonColor
import com.example.eksamensapp.ui.theme.TransparentRedBackgroundColor

@Composable
fun AnimeDetailsItem(
    animeDetailsViewModel: AnimeDetailsViewModel,
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
                .border(1.dp, LightGrayBorderColor, shape = RoundedCornerShape(16.dp))
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
                    .fillMaxSize()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Bottom

            ) {

                Text(text = "Year: ${currentAnime.year}", color = Color.White)
                Text(text = "Episodes: ${currentAnime.episodes}", color = Color.White)
                Text(text = "Genres: ${currentAnime.genres}", color = Color.White)
                Text(text = "Rating: ${currentAnime.score}", color = Color.White)

                Button(
                    modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        animeDetailsViewModel.toggleWatched(currentAnime)
                    },
                    colors = if (currentAnime.haveWatched) {
                        ButtonDefaults.buttonColors(
                            containerColor = SelectedButtonColor,
                            contentColor = Color.White
                        )
                    } else {
                        ButtonDefaults.buttonColors(
                            containerColor = DarkerRed,
                            contentColor = Color.White
                        )
                    },
                    border = if (currentAnime.haveWatched) {
                        BorderStroke(1.dp, SelectedBorderColor)
                    } else {
                        BorderStroke(1.dp, RedBackgroundColor)
                    },
                ) {
                    if (currentAnime.haveWatched) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Sett")
                    } else {
                        Text(text = "Merk som sett")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            border = BorderStroke(1.dp, LightGrayBorderColor),
            colors = CardDefaults.cardColors(
                containerColor = TransparentRedBackgroundColor
            )
        ) {
            Text(
                text = currentAnime.synopsis,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}