package com.nifa.fuel_buddy.fuelstation.presentation.navigation

import com.nifa.fuel_buddy.core.navigation.NavigationScreen
import kotlinx.serialization.Serializable

sealed interface FuelStationNavigation : NavigationScreen {

    @Serializable
    data object FuelNavGraph : FuelStationNavigation

    @Serializable
    data object Home : FuelStationNavigation
}