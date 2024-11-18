package com.nifa.fuel_buddy.auth.domain

import com.nifa.fuel_buddy.auth.domain.model.FuelStationSignIn
import com.nifa.fuel_buddy.auth.domain.model.UserSignIn
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignInRequest
import com.nifa.fuel_buddy.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun userSignIn(userSignInRequest: UserSignInRequest): Flow<Result<UserSignIn>>

    suspend fun fuelStationSignIn(fuelStationSignInRequest: FuelStationSignInRequest): Flow<Result<FuelStationSignIn>>

    suspend fun setUserPreferences(userSignIn: UserSignIn)

    suspend fun setFuelStationPreferences(fuelStationSignIn: FuelStationSignIn)
}