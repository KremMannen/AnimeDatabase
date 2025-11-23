package com.example.eksamensapp.screens.useridea

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.eksamensapp.components.AnimeFavoriteItem
import com.example.eksamensapp.components.AppHeader
import com.example.eksamensapp.components.UserIdeaItem
import com.example.eksamensapp.navigation.NavigationRoutes
import com.example.eksamensapp.screens.animedetails.AnimeDetailsViewModel
import com.example.eksamensapp.screens.watched.AnimeWatchedViewModel
import com.example.eksamensapp.ui.theme.DarkRedHeaderColor
import com.example.eksamensapp.ui.theme.LightGrayBorderColor
import com.example.eksamensapp.ui.theme.RedBackgroundColor

@Composable
fun AnimeUserIdeaScreen(
    animeUserIdeaViewModel: AnimeUserIdeaViewModel,
    navController: NavController
) {
    val userIdeas = animeUserIdeaViewModel.userIdeas.collectAsState()

    LaunchedEffect(Unit) {
        animeUserIdeaViewModel.showAll()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppHeader("Anime idèer")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                border = BorderStroke(1.dp, LightGrayBorderColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkRedHeaderColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Her kan du se dine egendefinerte anime-konsepter",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )

                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Button(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    onClick = {
                        navController.navigate(
                            NavigationRoutes.AddIdea
                        )
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding( top = 8.dp, bottom = 8.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        onClick = {
                            navController.navigate(
                                NavigationRoutes.DeleteIdea
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkRedHeaderColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Red)
                    ) {
                        Text(
                            text = "Slett idé",
                            fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            navController.navigate(
                                NavigationRoutes.DeleteIdea
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkRedHeaderColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Red)
                    ) {
                        Text(
                            text = "Oppdater idé",
                            fontSize = 16.sp)
                    }
                }

            }

            HorizontalDivider(
                thickness = 3.dp,
                color = LightGrayBorderColor
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                items(userIdeas.value) { userIdea ->
                    UserIdeaItem(
                        userIdea = userIdea,
                    )
                }
            }
        }
    }
}



