package com.nifa.fuel_buddy.core.datastore.user

data class UserPreferences(
    val userEmail: String,
    val userName: String,
    val userId: String,
    val userToken: String
)