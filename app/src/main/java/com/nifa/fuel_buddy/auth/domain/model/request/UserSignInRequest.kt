package com.nifa.fuel_buddy.auth.domain.model.request

data class UserSignInRequest(
    val userEmail: String,
    val password: String
)