package com.nifa.fuel_buddy.user.presentation.navigation

import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.user.domain.model.FuelStation
import kotlinx.serialization.Serializable

sealed interface UserNavigation : NavigationScreen {

    @Serializable
    data object HomeScreen : UserNavigation

    @Serializable
    data class HomeDetailScreen(val fuelStation: FuelStation) : UserNavigation

    @Serializable
    data object OrderStatusScreen : UserNavigation

    @Serializable
    data object ActivityScreen : UserNavigation

    @Serializable
    data class ActivityDetailScreen(val fuelStationName: String, val orderId: String, val deliveryCharge : Int) : UserNavigation

    @Serializable
    data object AccountScreen : UserNavigation

    @Serializable
    data class OrderTrackingScreen(val orderId : String) : UserNavigation
}