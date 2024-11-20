package com.nifa.fuel_buddy.auth.domain.model

import com.nifa.fuel_buddy.core.data.datastore.fuelstation.FuelStationPreferences

data class FuelStation(
    val bunkId: String,
    val bunkName: String,
    val bunkEmail: String,
    val bunkToken: String,
)

fun FuelStation.toFuelStationPreferences() = FuelStationPreferences(
    bunkId = bunkId,
    bunkName = bunkName,
    bunkEmail = bunkEmail,
    bunkToken = bunkToken
)
