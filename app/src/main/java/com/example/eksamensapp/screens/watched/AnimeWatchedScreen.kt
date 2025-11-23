package com.example.eksamensapp.screens.watched


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.eksamensapp.components.AnimeFavoriteItem
import com.example.eksamensapp.components.AppHeader
import com.example.eksamensapp.navigation.NavigationRoutes
import com.example.eksamensapp.ui.theme.DarkRed
import com.example.eksamensapp.ui.theme.LightGrayBorderColor


@Composable
fun AnimeWatchedScreen(
    animeWatchedViewModel: AnimeWatchedViewModel,
    navController: NavController
) {
    val animes = animeWatchedViewModel.animes.collectAsState()
    val totalWatchedCount = animeWatchedViewModel.totalWatchedCount.collectAsState()
    val backStackEntry = navController.currentBackStackEntryAsState().value
    var isFilteringFavorites by remember { mutableStateOf(false) }

    LaunchedEffect(backStackEntry) {
        animeWatchedViewModel.setAnimesByWatchedStatus()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppHeader("Du har sett ${totalWatchedCount.value} serier")


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
            border = BorderStroke(1.dp, LightGrayBorderColor),
            colors = CardDefaults.cardColors(containerColor = DarkRed),
            shape = RoundedCornerShape(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Antall favoritter: ${animes.value.count { it.isFavorite}}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        if (isFilteringFavorites) {
                            animeWatchedViewModel.setAnimesByWatchedStatus()
                        } else {
                            animeWatchedViewModel.filterFavorites()
                        }
                        isFilteringFavorites = !isFilteringFavorites
                        }
                ){
                    if (isFilteringFavorites) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Toggle Favorites",
                            tint = Color.White
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Toggle Favorites",
                            tint = LightGrayBorderColor
                        )
                    }
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 3.dp,
            color = LightGrayBorderColor
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            items(animes.value) { anime ->
                AnimeFavoriteItem(
                    animeEntity = anime,
                    animeWatchedViewModel = animeWatchedViewModel,
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



