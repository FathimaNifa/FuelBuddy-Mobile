package com.nifa.fuel_buddy.auth.domain.model.request

data class FuelStationSignInRequest(
    val fuelStationEmail: String,
    val password: String
)