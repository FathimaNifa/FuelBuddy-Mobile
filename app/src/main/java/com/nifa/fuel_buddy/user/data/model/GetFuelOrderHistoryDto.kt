package com.nifa.fuel_buddy.user.data.model

import com.nifa.fuel_buddy.user.domain.model.FuelOrderHistory

data class GetFuelOrderHistoryDto(
    val statusCode: Int,
    val message: String,
    val data: List<FuelOrderHistoryDto>
)

data class FuelOrderHistoryDto(
    val orderId: String,
    val orderDateTime: String,
    val totalPrice: String,
    val bunkId: String,
    val bunkName: String,
    val bunkImage: String,
    val awayFrom: Double,
    val bunkRating: Int,
    val bunkRatedUserCount: Int,
    val deliveryCharge : Int
)

fun FuelOrderHistoryDto.toFuelOrderHistory() = FuelOrderHistory(
    orderId = orderId,
    orderDateTime = orderDateTime,
    totalPrice = totalPrice,
    bunkId = bunkId,
    bunkName = bunkName,
    bunkImage = bunkImage,
    awayFrom = awayFrom,
    bunkRating = bunkRating,
    bunkRatedUserCount = bunkRatedUserCount,
    deliveryCharge = deliveryCharge
)

fun FuelOrderHistory.toFuelOrderHistoryDto() = FuelOrderHistoryDto(
    orderId = orderId,
    orderDateTime = orderDateTime,
    totalPrice = totalPrice,
    bunkId = bunkId,
    bunkName = bunkName,
    bunkImage = bunkImage,
    awayFrom = awayFrom,
    bunkRating = bunkRating,
    bunkRatedUserCount = bunkRatedUserCount,
    deliveryCharge = deliveryCharge
)

