package com.nifa.fuel_buddy.user.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val productId: String,
    val name: String,
    val imageUrl: String,
    val price: Long,
    val quantityAdded: Int
)