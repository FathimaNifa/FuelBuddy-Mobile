package com.nifa.fuel_buddy.fuelstation.domain.model

data class OrderDetails(
    val orderId: String,
    val orderNumber: String,
    val location: String,
    val totalPrice: String,
    val awayFrom: String,
    val userName: String
)