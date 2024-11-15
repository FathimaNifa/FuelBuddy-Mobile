package com.nifa.fuel_buddy.fuelstation.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.fuelNavGraph(navController: NavHostController) {
    navigation<FuelStationNavigation.FuelNavGraph>(
        startDestination = FuelStationNavigation.Home
    ) {

        composable<FuelStationNavigation.Home> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Fuel Station Home"
                )
            }
        }
    }
}