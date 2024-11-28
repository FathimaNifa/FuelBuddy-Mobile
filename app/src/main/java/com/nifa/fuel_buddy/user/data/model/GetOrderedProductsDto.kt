package com.nifa.fuel_buddy.user.data.model

import com.nifa.fuel_buddy.user.domain.model.Product

data class GetOrderedProductsDto(
    val statusCode: Int,
    val message: String,
    val data : List<OrderedProductsDto>
)

data class OrderedProductsDto(
    val productId: String,
    val productName: String,
    val productImage: String,
    val productPrice: Long,
    val quantityAdded: Int
)

fun OrderedProductsDto.toProduct() = Product(
    productId = productId,
    name = productName,
    imageUrl = productImage,
    price = productPrice,
    quantityAdded = quantityAdded
)

fun Product.toOrderedProductsDto() = OrderedProductsDto(
    productId = productId,
    productName = name,
    productImage = imageUrl,
    productPrice = price,
    quantityAdded = quantityAdded
)