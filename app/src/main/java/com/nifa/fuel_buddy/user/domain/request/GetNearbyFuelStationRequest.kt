package com.nifa.fuel_buddy.user.domain.request

data class GetNearbyFuelStationRequest(
    val userId : String,
    val latitude : String,
    val longitude : String
)