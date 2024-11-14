package com.nifa.fuel_buddy.user.domain

import kotlinx.serialization.Serializable

@Serializable
data class FuelStation(
    val name: String,
    val imageUrl: String,
    val distance: String,
    val deliveryCharge: Long,
    val productList: List<Product>
)

