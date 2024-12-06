package com.nifa.fuel_buddy.fuelstation.data.model

import com.nifa.fuel_buddy.user.domain.model.Product

data class GetOrderedProductDetailsDto(
    val statusCode: Int,
    val message: String,
    val data: List<ProductDetailsDto>
)

data class ProductDetailsDto(
    val productId: String,
    val productName: String,
    val productImage: String,
    val productPrice: Long,
    val quantity: Int
)

fun ProductDetailsDto.toProduct() = Product(
    productId = productId,
    name = productName,
    imageUrl = productImage,
    price = productPrice,
    quantityAdded = quantity
)

