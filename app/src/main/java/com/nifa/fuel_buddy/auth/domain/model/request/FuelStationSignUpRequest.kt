package com.nifa.fuel_buddy.auth.domain.model.request

data class FuelStationSignUpRequest(
    val bunkName: String,
    val bunkEmail: String,
    val password: String,
    val registrationNumber: String
)
