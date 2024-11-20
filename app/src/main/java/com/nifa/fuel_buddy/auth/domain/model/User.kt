package com.nifa.fuel_buddy.auth.domain.model

import com.nifa.fuel_buddy.core.data.datastore.user.UserPreferences

data class User(
    val userId: String,
    val userName: String,
    val userEmail: String,
    val userToken: String,
)

fun User.toUserPreferences() = UserPreferences(
    userId = userId,
    userName = userName,
    userEmail = userEmail,
    userToken = userToken
)
