package com.example.eksamensapp.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.eksamensapp.data.database.AnimeEntity
import com.example.eksamensapp.screens.watched.AnimeWatchedViewModel
import com.example.eksamensapp.ui.theme.DarkRedHeaderColor
import com.example.eksamensapp.ui.theme.RedBackgroundColor
import com.example.eksamensapp.ui.theme.SelectedBorderColor
import com.example.eksamensapp.ui.theme.SelectedButtonColor


@Composable
fun AnimeFavoriteItem(
    animeEntity: AnimeEntity,
    animeWatchedViewModel: AnimeWatchedViewModel,
    seeDetails: (() -> Unit)? = null
) {
    val isFavorite = animeEntity.isFavorite


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, SelectedBorderColor),
        onClick = {
            if (seeDetails != null) {
                seeDetails()
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            // Background image
            AsyncImage(
                model = animeEntity.imageUrl,
                contentDescription = animeEntity.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            // Gradient overlay for better text readability
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )

            Row {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = animeEntity.title.uppercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            if (isFavorite) {
                                animeWatchedViewModel.unsetFavorite(animeEntity)
                            } else {
                                animeWatchedViewModel.setFavorite(animeEntity)
                            }
                        },
                        colors = if (isFavorite) {
                            ButtonDefaults.buttonColors(
                                containerColor = SelectedButtonColor,
                                contentColor = Color.White
                            )
                        } else {
                            ButtonDefaults.buttonColors(
                                containerColor = DarkRedHeaderColor,
                                contentColor = Color.White
                            )
                        },
                        border = if (isFavorite) {
                            BorderStroke(1.dp, SelectedBorderColor)
                        } else {
                            BorderStroke(1.dp, RedBackgroundColor)
                        },
                    ) {
                        Text(text = if (isFavorite) "Fjern fra favoritter" else "Legg til favoritter")
                    }
                }
            }
        }
    }
}

