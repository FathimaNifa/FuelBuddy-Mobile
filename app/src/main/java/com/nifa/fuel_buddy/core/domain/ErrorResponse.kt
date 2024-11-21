package com.nifa.fuel_buddy.core.domain

data class ErrorResponse(
    val statusCode: Int,
    val message: String
)