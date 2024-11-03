package com.nifa.fuel_buddy.domain

data class FuelOrderHistory(
    val orderId: String,
    val orderDateTime: String,
    val price : String,
    val fuelStation: FuelStation
)