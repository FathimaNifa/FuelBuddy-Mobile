package com.nifa.fuel_buddy.user.domain

data class FuelOrderHistory(
    val orderId: String,
    val orderDateTime: String,
    val totalPrice : String,
    val fuelStation: FuelStation
)