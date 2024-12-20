package com.nifa.fuel_buddy.core.domain.util

data class ErrorResponse(
    val statusCode: Int,
    val message: String
)