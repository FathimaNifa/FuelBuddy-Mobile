package com.nifa.fuel_buddy.presentation.navigation

import com.nifa.fuel_buddy.domain.FuelStation
import kotlinx.serialization.Serializable

sealed interface NavigationScreen {

    @Serializable
    data object HomeScreen : NavigationScreen

    @Serializable
    data class HomeDetailScreen(val fuelStation: FuelStation) : NavigationScreen

    @Serializable
    data object ActivityScreen : NavigationScreen

    @Serializable
    data class ActivityDetailScreen(val fuelStation: FuelStation) : NavigationScreen

    @Serializable
    data object AccountScreen : NavigationScreen
}
