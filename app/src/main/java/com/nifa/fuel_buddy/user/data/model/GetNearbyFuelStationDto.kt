package com.nifa.fuel_buddy.user.data.model

import com.nifa.fuel_buddy.user.domain.model.FuelStation

data class GetNearbyFuelStationDto(
    val statusCode: Int,
    val message: String,
    val data: List<FuelStationDto>
)

data class FuelStationDto(
    val bunkId: String,
    val bunkName: String,
    val bunkImage: String,
    val awayFrom: Double,
    val bunkRating: Int,
    val bunkRatedUserCount: Int,
)

fun FuelStationDto.toFuelStation() = FuelStation(
    id = bunkId,
    name = bunkName,
    imageUrl = bunkImage,
    distance = awayFrom.toString(),
    rating = bunkRating,
    ratedUserCount = bunkRatedUserCount,
    deliveryCharge = 100
)

fun FuelStation.toFuelStationDto() = FuelStationDto(
    bunkId = id,
    bunkName = name,
    bunkImage = imageUrl,
    awayFrom = distance.toDouble(),
    bunkRating = rating,
    bunkRatedUserCount = ratedUserCount
)