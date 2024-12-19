package com.nifa.fuel_buddy.user.domain.request

import com.nifa.fuel_buddy.core.domain.model.LatLong

data class OrderProductsRequest(
    val bunkId: String,
    val deliveryLocation: LatLong,
    val cartItems: List<CartItem>
)

data class CartItem(
    val productId: String,
    val productQuantity: Int
)