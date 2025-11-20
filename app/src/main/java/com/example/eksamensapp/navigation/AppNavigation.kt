package com.example.eksamensapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.eksamensapp.screens.animedetails.AnimeDetailsScreen
import com.example.eksamensapp.screens.animedetails.AnimeDetailsViewModel
import com.example.eksamensapp.screens.watched.AnimeWatchedScreen
import com.example.eksamensapp.screens.watched.AnimeWatchedViewModel
import com.example.eksamensapp.screens.home.AnimeHomeScreen
import com.example.eksamensapp.screens.home.AnimeHomeViewModel
import com.example.eksamensapp.screens.search.AnimeSearchScreen
import com.example.eksamensapp.screens.search.AnimeSearchViewModel
import com.example.eksamensapp.screens.useridea.AnimeUserIdeaScreen
import com.example.eksamensapp.screens.useridea.AnimeUserIdeaViewModel



@Composable
fun AppNavigation(
    animeHomeViewModel: AnimeHomeViewModel,
    animeDetailsViewModel: AnimeDetailsViewModel,
    animeSearchViewModel: AnimeSearchViewModel,
    animeUserIdeaViewModel: AnimeUserIdeaViewModel,
    animeWatchedViewModel: AnimeWatchedViewModel
    ) {

    val navController = rememberNavController()

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(1)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedItemIndex == 1,
                    onClick = {
                        selectedItemIndex = 1
                        navController.navigate(NavigationRoutes.Home)
                    },
                    icon = {
                        if( selectedItemIndex == 1){
                            Icon(imageVector = Icons.Filled.Home,
                                contentDescription = null)
                        }else{
                            Icon(imageVector = Icons.Outlined.Home,
                                contentDescription = null)
                        }
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = selectedItemIndex == 2,
                    onClick = {
                        selectedItemIndex = 2
                        navController.navigate(NavigationRoutes.Search)
                    },
                    icon = {
                        if( selectedItemIndex == 2){
                            Icon(imageVector = Icons.Filled.Search,
                                contentDescription = null)
                        }else{
                            Icon(imageVector = Icons.Outlined.Search,
                                contentDescription = null)
                        }
                    },
                    label = {
                        Text("Search")
                    }
                )

                NavigationBarItem(
                    selected = selectedItemIndex == 3,
                    onClick = {
                        selectedItemIndex = 3
                        navController.navigate(NavigationRoutes.UserIdea)
                    },
                    icon = {
                        if( selectedItemIndex == 3){
                            Icon(imageVector = Icons.Filled.Create,
                                contentDescription = null)
                        }else{
                            Icon(imageVector = Icons.Outlined.Create,
                                contentDescription = null)
                        }
                    },
                    label = {
                        Text("Create idea")
                    }
                )

                NavigationBarItem(
                    selected = selectedItemIndex == 4,
                    onClick = {
                        selectedItemIndex = 4
                        navController.navigate(NavigationRoutes.Watched)
                    },
                    icon = {
                        if( selectedItemIndex == 4){
                            Icon(imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null)
                        }else{
                            Icon(imageVector = Icons.Outlined.Check,
                                contentDescription = null)
                        }
                    },
                    label = {
                        Text("Watched")
                    }
                )
            }
        }
    ) { innerPadding ->
        Column ( // Main column surrounding the entire app
            modifier = Modifier.padding(innerPadding)
        ){
            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.Home
            ) {
                composable <NavigationRoutes.Home> {
                    AnimeHomeScreen(animeHomeViewModel)
                }

                composable<NavigationRoutes.AnimeDetailsRoute> {backStackEntry ->
                    val args = backStackEntry.toRoute<NavigationRoutes.AnimeDetailsRoute>()
                    AnimeDetailsScreen(
                        animeDetailsViewModel,
                        navController,
                        args.id
                    )
                }

                composable <NavigationRoutes.Search> {
                    AnimeSearchScreen(animeSearchViewModel)
                }

                composable <NavigationRoutes.UserIdea> {
                    AnimeUserIdeaScreen(animeUserIdeaViewModel)
                }

                composable <NavigationRoutes.Watched> {
                    AnimeWatchedScreen(animeWatchedViewModel)
                }
            }
        }
    }
}

