package com.nifa.fuel_buddy.auth.data.model

import com.nifa.fuel_buddy.auth.domain.model.User

data class UserSignInDto(
    val statusCode: Int,
    val message: String,
    val data: UserSignInDataDto
)

data class UserSignInDataDto(
    val userId: String,
    val userName: String,
    val userEmail: String,
    val userToken: String,
)

fun UserSignInDto.toUser() = User(
    userId = data.userId,
    userEmail = data.userEmail,
    userToken = data.userToken,
    userName = data.userName
)
