package com.nifa.fuel_buddy.fuelstation.domain.model.request

data class AcceptOrderRequest(
    val orderId: String,
    val type: String
)