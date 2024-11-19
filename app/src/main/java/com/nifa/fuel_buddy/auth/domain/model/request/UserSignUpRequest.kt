package com.nifa.fuel_buddy.auth.domain.model.request

data class UserSignUpRequest(
    val userName : String,
    val userEmail : String,
    val password : String,
    val dlNumber : String,
    val dob : String
)
