package com.nifa.fuel_buddy.domain

import kotlinx.serialization.Serializable

@Serializable
data class FuelStation(
    val name: String,
    val imageUrl: String,
    val distance: String,
    val deliveryCharge: Long,
    val productList: List<Product>
)

@Serializable
data class Product(
    val name: String,
    val imageUrl: String,
    val price: Long,
    val quantityAdded: Int = 0
)