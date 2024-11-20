package com.nifa.fuel_buddy.auth.data.model

import com.nifa.fuel_buddy.auth.domain.model.FuelStation

data class FuelStationSignUpDto(
    val statusCode: Int,
    val message: String,
    val data: FuelStationSignUpDataDto
)

data class FuelStationSignUpDataDto(
    val bunkId: String,
    val bunkName: String,
    val bunkEmail: String,
    val bunkToken: String
)

fun FuelStationSignUpDto.toFuelStation() = FuelStation(
    bunkId = data.bunkId,
    bunkEmail = data.bunkEmail,
    bunkToken = data.bunkToken,
    bunkName = data.bunkName
)