package com.nifa.fuel_buddy.user.domain.model

data class FuelOrderHistory(
    val orderId: String,
    val orderDateTime: String,
    val totalPrice : String,
    val bunkId: String,
    val bunkName: String,
    val bunkImage: String,
    val awayFrom: Double,
    val bunkRating: Int,
    val bunkRatedUserCount: Int,
    val deliveryCharge : Int
)