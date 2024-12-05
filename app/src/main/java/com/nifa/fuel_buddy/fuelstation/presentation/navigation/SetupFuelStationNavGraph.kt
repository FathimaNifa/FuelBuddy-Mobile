package com.nifa.fuel_buddy.fuelstation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nifa.fuel_buddy.fuelstation.presentation.feature.account.AccountScreen
import com.nifa.fuel_buddy.fuelstation.presentation.feature.history.HistoryScreen
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.LiveOrderScreen

@Composable
fun SetupFuelStationNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: FuelStationNavigation
) {

    NavHost(
        navController = navHostController,
        modifier = modifier,
        startDestination = startDestination
    ) {

        composable<FuelStationNavigation.LiveOrderScreen> {
            LiveOrderScreen()
        }

        composable<FuelStationNavigation.HistoryScreen> {
            HistoryScreen()
        }

        composable<FuelStationNavigation.AccountScreen> {
            AccountScreen()
        }
    }

}