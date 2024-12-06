package com.nifa.fuel_buddy.fuelstation.data.model

import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderStatus

data class GetCustomerOrderDto(
    val statusCode: Int,
    val message: String,
    val data: List<OrderDetailsDto>
)

data class OrderDetailsDto(
    val orderId: String,
    val orderNumber: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val awayFrom: String,
    val userName: String,
    val totalPrice: String,
    val deliveryCharge: String,
    val orderStatus: String,
    val dateAndTime: String
)

fun OrderDetailsDto.toOrderDetails() = OrderDetails(
    orderId = orderId,
    orderNumber = orderNumber,
    location = location,
    latitude = latitude,
    longitude = longitude,
    awayFrom = awayFrom,
    userName = userName,
    totalPrice = totalPrice,
    deliveryCharge = deliveryCharge,
    dateAndTime = dateAndTime,
    orderStatus = OrderStatus.entries.firstOrNull { it.name == orderStatus } ?: OrderStatus.New
)