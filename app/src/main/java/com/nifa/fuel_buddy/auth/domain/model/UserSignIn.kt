package com.nifa.fuel_buddy.auth.domain.model

data class UserSignIn(
    val userId: String,
    val userName: String,
    val userEmail: String,
    val userToken: String,
)
