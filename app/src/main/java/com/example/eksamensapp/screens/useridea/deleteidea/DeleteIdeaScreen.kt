package com.example.eksamensapp.screens.useridea.deleteidea

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.eksamensapp.components.DetailsAppHeader

@Composable
fun DeleteIdeaScreen(
    deleteIdeaViewModel: DeleteIdeaViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DetailsAppHeader(
            title = "Slett idé",
            onBackClick = { navController.popBackStack() }
        )

    }
}
