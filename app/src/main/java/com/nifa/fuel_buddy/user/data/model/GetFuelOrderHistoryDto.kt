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
    val fuelStation: FuelStationDto,
)

fun FuelOrderHistoryDto.toFuelOrderHistory() = FuelOrderHistory(
    orderId = orderId,
    orderDateTime = orderDateTime,
    totalPrice = totalPrice,
    fuelStation = fuelStation.toFuelStation()
)

fun FuelOrderHistory.toFuelOrderHistoryDto() = FuelOrderHistoryDto(
    orderId = orderId,
    orderDateTime = orderDateTime,
    totalPrice = totalPrice,
    fuelStation = fuelStation.toFuelStationDto()
)

