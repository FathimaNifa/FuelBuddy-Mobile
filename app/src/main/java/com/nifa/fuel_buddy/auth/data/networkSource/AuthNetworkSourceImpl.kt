package com.nifa.fuel_buddy.auth.data.networkSource

import android.content.Context
import com.nifa.fuel_buddy.auth.data.AuthApi
import com.nifa.fuel_buddy.auth.data.model.FuelStationSignInDto
import com.nifa.fuel_buddy.auth.data.model.FuelStationSignUpDto
import com.nifa.fuel_buddy.auth.data.model.UserSignInDto
import com.nifa.fuel_buddy.auth.data.model.UserSignupDto
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignUpRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignUpRequest
import com.nifa.fuel_buddy.core.domain.BaseApiResponse
import com.nifa.fuel_buddy.core.domain.Error
import com.nifa.fuel_buddy.core.domain.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthNetworkSourceImpl @Inject constructor(
    private val authApi: AuthApi,
    @ApplicationContext context: Context
) : BaseApiResponse(context), AuthNetworkSource {
    override suspend fun userSignIn(userSignInRequest: UserSignInRequest): Flow<Result<UserSignInDto, Error>> {
        return safeApiCall { authApi.userSignIn(userSignInRequest) }
    }

    override suspend fun fuelStationSignIn(fuelStationSignInRequest: FuelStationSignInRequest): Flow<Result<FuelStationSignInDto, Error>> {
        return safeApiCall { authApi.fuelStationSignIn(fuelStationSignInRequest) }
    }

    override suspend fun userSignUp(userSignUpRequest: UserSignUpRequest): Flow<Result<UserSignupDto, Error>> {
        return safeApiCall { authApi.userSignup(userSignUpRequest) }
    }

    override suspend fun fuelStationSignUp(fuelStationSignUpRequest: FuelStationSignUpRequest): Flow<Result<FuelStationSignUpDto, Error>> {
        return safeApiCall { authApi.fuelStationSignup(fuelStationSignUpRequest) }
    }
}