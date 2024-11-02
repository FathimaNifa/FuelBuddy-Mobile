package com.nifa.fuel_buddy.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationScreen {

    @Serializable
    data object HomeScreen : NavigationScreen

    @Serializable
    data object ActivityScreen : NavigationScreen

    @Serializable
    data object AccountScreen : NavigationScreen
}
