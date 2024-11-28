package com.nifa.fuel_buddy.user.domain.model

data class FuelOrderHistory(
    val orderId: String,
    val orderDateTime: String,
    val totalPrice : String,
    val fuelStation: FuelStation,
)