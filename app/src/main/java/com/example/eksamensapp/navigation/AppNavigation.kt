package com.example.eksamensapp.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.eksamensapp.screens.animedetails.AnimeDetailsScreen
import com.example.eksamensapp.screens.animedetails.AnimeDetailsViewModel
import com.example.eksamensapp.screens.home.AnimeHomeScreen
import com.example.eksamensapp.screens.home.AnimeHomeViewModel
import com.example.eksamensapp.screens.search.AnimeSearchScreen
import com.example.eksamensapp.screens.search.AnimeSearchViewModel
import com.example.eksamensapp.screens.useridea.AnimeUserIdeaScreen
import com.example.eksamensapp.screens.useridea.AnimeUserIdeaViewModel
import com.example.eksamensapp.screens.useridea.addidea.AddIdeaScreen
import com.example.eksamensapp.screens.useridea.addidea.AddIdeaViewModel
import com.example.eksamensapp.screens.useridea.deleteidea.DeleteIdeaScreen
import com.example.eksamensapp.screens.useridea.deleteidea.DeleteIdeaViewModel
import com.example.eksamensapp.screens.useridea.updateidea.UpdateIdeaScreen
import com.example.eksamensapp.screens.useridea.updateidea.UpdateIdeaViewModel
import com.example.eksamensapp.screens.useridea.updateidea.editidea.EditIdeaScreen
import com.example.eksamensapp.screens.watched.AnimeWatchedScreen
import com.example.eksamensapp.screens.watched.AnimeWatchedViewModel
import com.example.eksamensapp.ui.theme.AppBackgroundColor
import com.example.eksamensapp.ui.theme.RedBackgroundColor
import com.example.eksamensapp.ui.theme.SelectedBackgroundColor
import com.example.eksamensapp.ui.theme.SelectedBorderColor
import com.example.eksamensapp.ui.theme.SelectedTextColor
import com.example.eksamensapp.ui.theme.UnselectedBackgroundColor
import com.example.eksamensapp.ui.theme.UnselectedBorderColor
import com.example.eksamensapp.ui.theme.UnselectedTextColor

private val boxShape = RoundedCornerShape(16.dp)


@Composable
fun AppNavigation(
    animeHomeViewModel: AnimeHomeViewModel,
    animeDetailsViewModel: AnimeDetailsViewModel,
    animeSearchViewModel: AnimeSearchViewModel,
    animeUserIdeaViewModel: AnimeUserIdeaViewModel,
    animeWatchedViewModel: AnimeWatchedViewModel,
    addIdeaViewModel: AddIdeaViewModel,
    deleteIdeaViewModel: DeleteIdeaViewModel,
    updateIdeaViewModel: UpdateIdeaViewModel
    ) {

    val navController = rememberNavController()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(1)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    fun shouldShowBottomBar(currentRoute: String?): Boolean {
        val hiddenRoutes = listOf(
            "com.example.eksamensapp.navigation.NavigationRoutes.AnimeDetailsRoute",
            "com.example.eksamensapp.navigation.NavigationRoutes.AddIdea",
            "com.example.eksamensapp.navigation.NavigationRoutes.DeleteIdea"
        )

        if (currentRoute == null) return true

        for (hiddenRoute in hiddenRoutes) {
            if (currentRoute.startsWith(hiddenRoute)) {
                return false
            }
        }
        return true
    }

    val showBottomBar = shouldShowBottomBar(currentRoute)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppBackgroundColor,

        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar(
                    containerColor = RedBackgroundColor
                ) {
                    NavigationBarItem(
                        selected = selectedItemIndex == 1,
                        onClick = {
                            selectedItemIndex = 1
                            navController.navigate(NavigationRoutes.Home)
                        },
                        icon = {
                            Box(
                                modifier = Modifier
                                    .width(88.dp)
                                    .height(52.dp)
                                    .clip(boxShape)
                                    .background(
                                        if (selectedItemIndex == 1) {
                                            SelectedBackgroundColor
                                        } else {
                                            UnselectedBackgroundColor
                                        }
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (selectedItemIndex == 1) {
                                            SelectedBorderColor
                                        } else {
                                            UnselectedBorderColor
                                        },
                                        shape = boxShape
                                    )
                                    .padding(horizontal = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    if (selectedItemIndex == 1) {
                                        Icon(
                                            imageVector = Icons.Filled.Home,
                                            contentDescription = null
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.Home,
                                            contentDescription = null
                                        )
                                    }

                                    Text(
                                        text = "Hjem",
                                        color = if (selectedItemIndex == 1) SelectedTextColor else UnselectedTextColor,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        },
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SelectedTextColor,
                            unselectedIconColor = UnselectedTextColor,
                            indicatorColor = UnselectedBackgroundColor
                        )
                    )

                    NavigationBarItem(
                        selected = selectedItemIndex == 2,
                        onClick = {
                            selectedItemIndex = 2
                            navController.navigate(NavigationRoutes.Search)
                        },
                        icon = {
                            val shape = RoundedCornerShape(16.dp)

                            Box(
                                modifier = Modifier
                                    .width(88.dp)
                                    .height(52.dp)
                                    .clip(shape)
                                    .background(
                                        if (selectedItemIndex == 2) {
                                            SelectedBackgroundColor
                                        } else {
                                            UnselectedBackgroundColor
                                        }
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (selectedItemIndex == 2) {
                                            SelectedBorderColor
                                        } else {
                                            UnselectedBorderColor
                                        },
                                        shape = boxShape
                                    )
                                    .padding(horizontal = 12.dp),
                                contentAlignment = Alignment.Center

                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    if (selectedItemIndex == 2) {
                                        Icon(
                                            imageVector = Icons.Filled.Search,
                                            contentDescription = null
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.Search,
                                            contentDescription = null
                                        )
                                    }

                                    Text(
                                        text = "Søk",
                                        color = if (selectedItemIndex == 2) Color.Red else Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        },
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Red,
                            unselectedIconColor = Color.White,
                            indicatorColor = Color.Transparent
                        )
                    )

                    NavigationBarItem(
                        selected = selectedItemIndex == 3,
                        onClick = {
                            selectedItemIndex = 3
                            navController.navigate(NavigationRoutes.UserIdea)
                        },
                        icon = {
                            val shape = RoundedCornerShape(16.dp)

                            Box(
                                modifier = Modifier
                                    .width(88.dp)
                                    .height(52.dp)
                                    .clip(shape)
                                    .background(
                                        if (selectedItemIndex == 3) {
                                            SelectedBackgroundColor
                                        } else {
                                            UnselectedBackgroundColor
                                        }
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (selectedItemIndex == 3) {
                                            SelectedBorderColor
                                        } else {
                                            UnselectedBorderColor
                                        },
                                        shape = boxShape
                                    )
                                    .padding(horizontal = 12.dp),
                                contentAlignment = Alignment.Center

                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    if (selectedItemIndex == 3) {
                                        Icon(
                                            imageVector = Icons.Filled.Create,
                                            contentDescription = null
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.Create,
                                            contentDescription = null
                                        )
                                    }

                                    Text(
                                        text = "Idéer",
                                        color = if (selectedItemIndex == 3) Color.Red else Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        },
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Red,
                            unselectedIconColor = Color.White,
                            indicatorColor = Color.Transparent
                        )
                    )

                    NavigationBarItem(
                        selected = selectedItemIndex == 4,
                        onClick = {
                            selectedItemIndex = 4
                            navController.navigate(NavigationRoutes.Watched)
                        },
                        icon = {
                            val shape = RoundedCornerShape(16.dp)

                            Box(
                                modifier = Modifier
                                    .width(88.dp)
                                    .height(52.dp)
                                    .clip(shape)
                                    .background(
                                        if (selectedItemIndex == 4) {
                                            SelectedBackgroundColor
                                        } else {
                                            UnselectedBackgroundColor
                                        }
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (selectedItemIndex == 4) {
                                            SelectedBorderColor
                                        } else {
                                            UnselectedBorderColor
                                        },
                                        shape = boxShape
                                    )
                                    .padding(horizontal = 12.dp),
                                contentAlignment = Alignment.Center

                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    if (selectedItemIndex == 4) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = null
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.Check,
                                            contentDescription = null
                                        )
                                    }

                                    Text(
                                        text = "Dine serier",
                                        color = if (selectedItemIndex == 4) Color.Red else Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        },
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Red,
                            unselectedIconColor = Color.White,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
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
                    AnimeHomeScreen(animeHomeViewModel, navController)
                }

                composable<NavigationRoutes.AnimeDetailsRoute> {backStackEntry ->
                    val args = backStackEntry.toRoute<NavigationRoutes.AnimeDetailsRoute>()
                    AnimeDetailsScreen(
                        animeDetailsViewModel,
                        navController,
                        args.id
                    )
                }

                composable<NavigationRoutes.EditIdea> {backStackEntry ->
                    val args = backStackEntry.toRoute<NavigationRoutes.EditIdea>()
                    EditIdeaScreen(
                        updateIdeaViewModel,
                        navController,
                        args.id
                    )
                }

                composable <NavigationRoutes.Search> {
                    AnimeSearchScreen(animeSearchViewModel, navController)
                }

                composable <NavigationRoutes.UserIdea> {
                    AnimeUserIdeaScreen(animeUserIdeaViewModel, navController)
                }

                composable <NavigationRoutes.Watched> {
                    AnimeWatchedScreen(animeWatchedViewModel, navController)
                }

                composable < NavigationRoutes.AddIdea> {
                    AddIdeaScreen(addIdeaViewModel, navController)
                }

                composable < NavigationRoutes.DeleteIdea> {
                    DeleteIdeaScreen(deleteIdeaViewModel, navController)
                }

                composable < NavigationRoutes.UpdateIdea> {
                    UpdateIdeaScreen(updateIdeaViewModel, navController)
                }
            }
        }
    }
}

