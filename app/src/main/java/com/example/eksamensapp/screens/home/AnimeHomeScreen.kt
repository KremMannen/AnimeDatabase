package com.example.eksamensapp.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eksamensapp.components.AnimeCardItem
import com.example.eksamensapp.components.AppHeader
import com.example.eksamensapp.navigation.NavigationRoutes
import com.example.eksamensapp.ui.theme.DarkRedHeaderColor
import com.example.eksamensapp.ui.theme.LightGrayBorderColor


@Composable
fun AnimeHomeScreen(
    animeHomeViewModel: AnimeHomeViewModel,
    navController : NavController,
){
    val animes = animeHomeViewModel.animes.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppHeader("Anime Database")

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .height(150.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, LightGrayBorderColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkRedHeaderColor),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Velkommen!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = "Her får du oversikt over de mest populære animeene og muligheten til å skape dine egne idéer!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            items(animes.value) { anime ->
                AnimeCardItem(
                    anime,
                    seeDetails = {
                        navController.navigate(
                            NavigationRoutes.AnimeDetailsRoute(
                                anime.id
                            )
                        )
                    }
                )
            }
        }
    }
}