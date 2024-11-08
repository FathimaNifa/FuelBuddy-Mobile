package com.nifa.fuel_buddy.domain

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val productId : Long,
    val name: String,
    val imageUrl: String,
    val price: Long,
    val quantityAdded: Int
)