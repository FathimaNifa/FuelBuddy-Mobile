package com.nifa.fuel_buddy.fuelstation.domain.model.request

data class UpdateDriverLocationRequest(
    val orderId : String,
    val latitude : Double,
    val longitude : Double
)
