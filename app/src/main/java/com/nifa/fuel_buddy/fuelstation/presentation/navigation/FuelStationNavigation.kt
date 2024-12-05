package com.nifa.fuel_buddy.fuelstation.presentation.navigation

import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import kotlinx.serialization.Serializable

sealed interface FuelStationNavigation : NavigationScreen {

    @Serializable
    data object LiveOrderScreen : FuelStationNavigation

    @Serializable
    data object LiveOrderDetailScreen : FuelStationNavigation

    @Serializable
    data object HistoryScreen : FuelStationNavigation

    @Serializable
    data object HistoryDetailScreen : FuelStationNavigation

    @Serializable
    data object AccountScreen : FuelStationNavigation
}