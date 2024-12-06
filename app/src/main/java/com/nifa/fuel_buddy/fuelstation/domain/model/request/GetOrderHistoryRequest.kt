package com.nifa.fuel_buddy.fuelstation.domain.model.request

data class GetOrderHistoryRequest(
    val bunkId : String,
    val bunkToken : String
)