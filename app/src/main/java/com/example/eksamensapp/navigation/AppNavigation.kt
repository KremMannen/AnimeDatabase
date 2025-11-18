package com.example.eksamensapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Home
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.room.screens.cardetails.CarDetailsScreen
import com.example.room.screens.cardetails.CarDetailsViewModel
import com.example.room.screens.home.HomeScreen
import com.example.room.screens.cars.CarScreen
import com.example.room.screens.cars.CarViewModel


@Composable
fun AppNavigation(
    carListViewModel: CarViewModel,
    carDetailsViewModel: CarDetailsViewModel
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
                        navController.navigate(NavigationRoutes.Cars)
                    },
                    icon = {
                        if( selectedItemIndex == 2){
                            Icon(imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null)
                        }else{
                            Icon(imageVector = Icons.Outlined.Check,
                                contentDescription = null)
                        }
                    },
                    label = {
                        Text("Siste Kjøp")
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
                    HomeScreen()
                }
                composable <NavigationRoutes.Cars> {
                    CarScreen(carListViewModel, navController)
                }
                composable<NavigationRoutes.CarsDetailsRoute> {backStackEntry ->
                    val args = backStackEntry.toRoute<NavigationRoutes.CarsDetailsRoute>()
                    CarDetailsScreen(
                        carDetailsViewModel,
                        navController,
                        args.carId
                    )
                }
            }
        }
    }
}

