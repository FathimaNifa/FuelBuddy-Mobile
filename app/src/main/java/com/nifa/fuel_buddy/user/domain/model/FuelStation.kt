package com.nifa.fuel_buddy.user.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FuelStation(
    val id : String,
    val name: String,
    val imageUrl: String,
    val distance: String,
    val rating : Int,
    val ratedUserCount : Int,
    val deliveryCharge: Int,
)

