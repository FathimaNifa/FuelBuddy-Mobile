package com.nifa.fuel_buddy.auth.data.model

import com.nifa.fuel_buddy.auth.domain.model.FuelStation

data class FuelStationSignInDto(
    val statusCode: Int,
    val message: String,
    val data: FuelStationSignInDataDto
)

data class FuelStationSignInDataDto(
    val bunkId: String,
    val bunkName: String,
    val bunkEmail: String,
    val bunkToken: String
)

fun FuelStationSignInDto.toFuelStation() = FuelStation(
    bunkId = data.bunkId,
    bunkEmail = data.bunkEmail,
    bunkToken = data.bunkToken,
    bunkName = data.bunkName
)