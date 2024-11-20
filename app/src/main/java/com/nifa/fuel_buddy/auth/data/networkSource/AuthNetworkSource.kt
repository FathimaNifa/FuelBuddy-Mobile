package com.nifa.fuel_buddy.auth.data.networkSource

import com.nifa.fuel_buddy.auth.data.model.FuelStationSignInDto
import com.nifa.fuel_buddy.auth.data.model.FuelStationSignUpDto
import com.nifa.fuel_buddy.auth.data.model.UserSignInDto
import com.nifa.fuel_buddy.auth.data.model.UserSignupDto
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignUpRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignUpRequest
import com.nifa.fuel_buddy.core.domain.Error
import com.nifa.fuel_buddy.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface AuthNetworkSource {

    suspend fun userSignIn(userSignInRequest: UserSignInRequest): Flow<Result<UserSignInDto, Error>>

    suspend fun fuelStationSignIn(fuelStationSignInRequest: FuelStationSignInRequest): Flow<Result<FuelStationSignInDto, Error>>

    suspend fun userSignUp(userSignUpRequest: UserSignUpRequest): Flow<Result<UserSignupDto, Error>>

    suspend fun fuelStationSignUp(fuelStationSignUpRequest: FuelStationSignUpRequest): Flow<Result<FuelStationSignUpDto, Error>>
}