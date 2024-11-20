package com.nifa.fuel_buddy.core.presentation.navigation

import kotlinx.serialization.Serializable


sealed interface NavGraphs : NavigationScreen {

    @Serializable
    data object UserNavGraph : NavGraphs

    @Serializable
    data object AuthNavGraph : NavGraphs

    @Serializable
    data object FuelStationNavGraph : NavGraphs
}