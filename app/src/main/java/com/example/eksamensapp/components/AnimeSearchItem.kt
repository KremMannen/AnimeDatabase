package com.example.eksamensapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.eksamensapp.data.database.AnimeEntity
import com.example.eksamensapp.ui.theme.SelectedBorderColor

@Composable
fun AnimeSearchThumbnail(
    anime: AnimeEntity,
    seeDetails: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .wrapContentSize(), // Use wrapContentSize to let the image define the size
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
                .wrapContentSize() // Ensure the Box also wraps the content size
        ) {
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = anime.title,
                modifier = Modifier
                    .fillMaxWidth(), // Fill the width of the Box/Card
                contentScale = ContentScale.Crop // Crop to maintain aspect ratio
            )
        }
    }
}