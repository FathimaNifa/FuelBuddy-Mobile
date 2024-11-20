package com.nifa.fuel_buddy.fuelstation.presentation.navigation

import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import kotlinx.serialization.Serializable

sealed interface FuelStationNavigation : NavigationScreen {

    @Serializable
    data object Home : FuelStationNavigation
}