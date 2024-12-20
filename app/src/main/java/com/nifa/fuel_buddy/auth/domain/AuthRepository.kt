package com.nifa.fuel_buddy.auth.domain

import com.nifa.fuel_buddy.auth.domain.model.FuelStation
import com.nifa.fuel_buddy.auth.domain.model.User
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignUpRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignUpRequest
import com.nifa.fuel_buddy.core.domain.util.NetworkError
import com.nifa.fuel_buddy.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun userSignIn(userSignInRequest: UserSignInRequest): Flow<Result<User, NetworkError>>

    suspend fun fuelStationSignIn(fuelStationSignInRequest: FuelStationSignInRequest): Flow<Result<FuelStation, NetworkError>>

    suspend fun setUserPreferences(user: User)

    suspend fun setFuelStationPreferences(fuelStation: FuelStation)

    suspend fun userSignUp(userSignUpRequest: UserSignUpRequest): Flow<Result<User, NetworkError>>

    suspend fun fuelStationSignUp(fuelStationSignUpRequest: FuelStationSignUpRequest): Flow<Result<FuelStation, NetworkError>>
}