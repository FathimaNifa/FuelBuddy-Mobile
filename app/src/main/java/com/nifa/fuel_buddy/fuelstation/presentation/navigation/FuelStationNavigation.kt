package com.nifa.fuel_buddy.fuelstation.presentation.navigation

import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
import kotlinx.serialization.Serializable

sealed interface FuelStationNavigation : NavigationScreen {

    @Serializable
    data object LiveOrderScreen : FuelStationNavigation

    @Serializable
    data class LiveOrderDetailScreen(val orderDetails: OrderDetails) : FuelStationNavigation

    @Serializable
    data class OutForDeliveryScreen(val latitude : Double, val longitude : Double, val orderId : String) : FuelStationNavigation

    @Serializable
    data object HistoryScreen : FuelStationNavigation

    @Serializable
    data class HistoryDetailScreen(val orderDetails: OrderDetails) : FuelStationNavigation

    @Serializable
    data object AccountScreen : FuelStationNavigation
}