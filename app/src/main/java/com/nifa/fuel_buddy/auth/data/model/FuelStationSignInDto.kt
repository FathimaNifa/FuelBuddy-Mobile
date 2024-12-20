package com.nifa.fuel_buddy.auth.data.model

import com.google.gson.annotations.SerializedName
import com.nifa.fuel_buddy.auth.domain.model.FuelStation

data class FuelStationSignInDto(
    val statusCode: Int,
    val message: String,
    val data: FuelStationSignInDataDto
)

data class FuelStationSignInDataDto(
    @SerializedName("userId")
    val bunkId: String,
    @SerializedName("userName")
    val bunkName: String,
    @SerializedName("userEmail")
    val bunkEmail: String,
    @SerializedName("userToken")
    val bunkToken: String
)

fun FuelStationSignInDto.toFuelStation() = FuelStation(
    bunkId = data.bunkId,
    bunkEmail = data.bunkEmail,
    bunkToken = data.bunkToken,
    bunkName = data.bunkName
)