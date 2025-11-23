package com.example.eksamensapp.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.eksamensapp.ui.theme.SelectedBorderColor


@Composable
fun AnimeFavoriteItem(
    animeEntity: AnimeEntity,
    animeWatchedViewModel: AnimeWatchedViewModel,
    seeDetails: (() -> Unit)? = null
) {

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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = animeEntity.title.uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    lineHeight = 26.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                IconButton(
                    onClick = {
                        animeWatchedViewModel.toggleFavorite(animeEntity)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    if (animeEntity.isFavorite) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Remove from favorites icon",
                            tint = Color.Red,
                            modifier = Modifier.size(38.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Add to favorites icon",
                            tint = Color.Red,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}


