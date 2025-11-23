package com.example.eksamensapp.screens.useridea.updateidea

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eksamensapp.components.AnimeSearchItem
import com.example.eksamensapp.components.AppHeader
import com.example.eksamensapp.components.DeleteUserIdeaItem
import com.example.eksamensapp.components.DetailsAppHeader
import com.example.eksamensapp.components.GenreSelectionItem
import com.example.eksamensapp.components.SearchIdeasItem
import com.example.eksamensapp.components.UpdateUserIdeaItem
import com.example.eksamensapp.data.database.UserIdeaDbRepository
import com.example.eksamensapp.data.database.UserIdeaEntity
import com.example.eksamensapp.navigation.NavigationRoutes
import com.example.eksamensapp.screens.useridea.addidea.AddIdeaViewModel
import com.example.eksamensapp.ui.theme.DarkGreyTransparent
import com.example.eksamensapp.ui.theme.DarkRedHeaderColor
import com.example.eksamensapp.ui.theme.LightGrayBorderColor
import com.example.eksamensapp.ui.theme.RedBackgroundColor
import com.example.eksamensapp.ui.theme.SelectedBorderColor
import com.example.eksamensapp.ui.theme.SelectedButtonColor
import com.example.eksamensapp.ui.theme.TransparentRedBackgroundColor

@Composable
fun UpdateIdeaScreen(
    updateIdeaViewModel: UpdateIdeaViewModel,
    navController: NavController
) {
    val results = updateIdeaViewModel.searchedIdea.collectAsState()

    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        updateIdeaViewModel.showAll()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        DetailsAppHeader(
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
                        .background(DarkRedHeaderColor),
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
                searchText = searchText,
                onSearchTextChange = { searchText = it },
                onSearchClick = { updateIdeaViewModel.handleInput(searchText) },
                results = results.value,
                itemContent = { userIdea ->
                    UpdateUserIdeaItem(
                        userIdea,
                        updateIdeaViewModel,
                        navController
                    )
                }
            )
        }
    }
}