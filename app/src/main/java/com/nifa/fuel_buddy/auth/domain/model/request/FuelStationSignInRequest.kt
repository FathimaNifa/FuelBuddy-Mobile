package com.nifa.fuel_buddy.auth.domain.model.request

data class FuelStationSignInRequest(
    val userEmail: String,
    val password: String
)