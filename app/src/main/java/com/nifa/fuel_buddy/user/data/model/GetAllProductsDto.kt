package com.nifa.fuel_buddy.user.data.model

import com.nifa.fuel_buddy.user.domain.model.Product

data class GetAllProductsDto(
    val statusCode: Int,
    val message: String,
    val data: List<ProductDto>
)

data class ProductDto(
    val productId: String,
    val productName: String,
    val productImage: String,
    val productPrice: Long
)

fun ProductDto.toProduct() = Product(
    productId = productId,
    name = productName,
    imageUrl = productImage,
    price = productPrice,
    quantityAdded = 0
)

fun Product.toProductDto() = ProductDto(
    productId = productId,
    productName = name,
    productImage = imageUrl,
    productPrice = price
)