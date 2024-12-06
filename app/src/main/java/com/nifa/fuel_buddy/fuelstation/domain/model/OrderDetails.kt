package com.nifa.fuel_buddy.fuelstation.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderDetails(
    val orderId: String,
    val orderNumber: String,
    val location: String,
    val totalPrice: String,
    val awayFrom: String,
    val latitude: Double,
    val longitude: Double,
    val userName: String,
    val deliveryCharge: String,
    val orderStatus: OrderStatus,
    val dateAndTime: String
)