package com.nifa.fuel_buddy.auth.domain.model

data class FuelStationSignIn(
    val bunkId: String,
    val bunkName: String,
    val bunkEmail: String,
    val bunkToken: String,
)
