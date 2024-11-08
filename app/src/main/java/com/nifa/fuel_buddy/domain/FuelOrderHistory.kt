package com.nifa.fuel_buddy.domain

data class FuelOrderHistory(
    val orderId: String,
    val orderDateTime: String,
    val totalPrice : String,
    val fuelStation: FuelStation
)