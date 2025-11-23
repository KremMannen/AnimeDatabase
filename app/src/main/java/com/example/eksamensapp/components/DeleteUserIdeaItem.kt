package com.example.eksamensapp.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.sp
import com.example.eksamensapp.screens.useridea.deleteidea.DeleteIdeaViewModel
import com.example.eksamensapp.ui.theme.DarkRedHeaderColor
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.eksamensapp.data.database.UserIdeaEntity
import com.example.eksamensapp.ui.theme.DarkGrayCardColor

@Composable
fun DeleteUserIdeaItem(
    userIdea: UserIdeaEntity,
    deleteIdeaViewModel: DeleteIdeaViewModel,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(
            containerColor = DarkGrayCardColor
        ),
        onClick = {
            onClick?.invoke()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Title
                Text(
                    text = userIdea.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = userIdea.id.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                )

            }

            // Genres
            Text(
                text = userIdea.genres,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            // Synopsis
            Text(
                text = userIdea.synopsis,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Button(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                onClick = {
                    deleteIdeaViewModel.handleDeleteIdea(userIdea.id)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkRedHeaderColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.Red)
            ) {
                Text(
                    text = "Lag anime idé",
                    fontSize = 16.sp)
            }
        }
    }
}