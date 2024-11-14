package com.nifa.fuel_buddy.presentation.core.navigation

import com.nifa.fuel_buddy.domain.FuelStation
import kotlinx.serialization.Serializable

sealed interface UserNavigation : NavigationScreen {

    @Serializable
    data object UserNavGraph : UserNavigation

    @Serializable
    data object HomeScreen : UserNavigation

    @Serializable
    data class HomeDetailScreen(val fuelStation: FuelStation) : UserNavigation

    @Serializable
    data object OrderStatusScreen : UserNavigation

    @Serializable
    data object ActivityScreen : UserNavigation

    @Serializable
    data class ActivityDetailScreen(val fuelStation: FuelStation) : UserNavigation

    @Serializable
    data object AccountScreen : UserNavigation
}