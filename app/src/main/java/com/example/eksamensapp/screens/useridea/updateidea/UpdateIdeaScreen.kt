package com.example.eksamensapp.screens.useridea.updateidea

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eksamensapp.components.AppHeaderItem
import com.example.eksamensapp.components.SearchIdeasItem
import com.example.eksamensapp.components.UpdateUserIdeaItem
import com.example.eksamensapp.ui.theme.DarkRed
import com.example.eksamensapp.ui.theme.LightGrayBorderColor

@Composable
fun UpdateIdeaScreen(
    updateIdeaViewModel: UpdateIdeaViewModel,
    navController: NavController
) {
    val results = updateIdeaViewModel.searchedIdea.collectAsState()



    LaunchedEffect(Unit) {
        updateIdeaViewModel.showAll()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        AppHeaderItem(
            title = "Oppdater idé",
            onBackClick = { navController.popBackStack() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp),
                border = BorderStroke(1.dp, LightGrayBorderColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkRed),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Oppdater Anime-konsept",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Her får du full oversikt over dine idéer og muligheten til å oppdatere dem",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            SearchIdeasItem(
                onSearchClick = { searchText ->
                    updateIdeaViewModel.handleInput(searchText)
                },
                results = results.value,
                itemContent = { userIdea ->
                    UpdateUserIdeaItem(userIdea, updateIdeaViewModel, navController)
                }
            )
        }
    }
}