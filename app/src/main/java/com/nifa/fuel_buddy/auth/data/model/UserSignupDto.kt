package com.nifa.fuel_buddy.auth.data.model

import com.nifa.fuel_buddy.auth.domain.model.User

data class UserSignupDto(
    val statusCode: Int,
    val message: String,
    val data: UserSignUpDataDto
)

data class UserSignUpDataDto(
    val userId: String,
    val userName: String,
    val userEmail: String,
    val userToken: String,
)

fun UserSignupDto.toUser() = User(
    userId = data.userId,
    userName = data.userName,
    userEmail = data.userEmail,
    userToken = data.userToken
)