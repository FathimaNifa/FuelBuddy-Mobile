package com.nifa.fuel_buddy.user.data.model

import com.nifa.fuel_buddy.user.domain.model.OrderProductData

data class OrderProductsDto(
    val statusCode: Int,
    val message: String,
    val data: OrderProductsDataDto
)

data class OrderProductsDataDto(
    val orderId: String
)

fun OrderProductsDataDto.toOrderProductData() = OrderProductData(
    orderId = orderId
)