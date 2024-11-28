package com.nifa.fuel_buddy.auth.data.networkSource

import com.nifa.fuel_buddy.auth.data.model.FuelStationSignInDto
import com.nifa.fuel_buddy.auth.data.model.FuelStationSignUpDto
import com.nifa.fuel_buddy.auth.data.model.UserSignInDto
import com.nifa.fuel_buddy.auth.data.model.UserSignupDto
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignUpRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("user/userSignUp")
    suspend fun userSignup(@Body userSignUpRequest: UserSignUpRequest): Response<UserSignupDto>

    @POST("user/signIn")
    suspend fun userSignIn(@Body userSignInRequest: UserSignInRequest): Response<UserSignInDto>

    @POST("bunk/bunkSignUp")
    suspend fun fuelStationSignup(@Body fuelStationSignupRequest: FuelStationSignUpRequest): Response<FuelStationSignUpDto>

    @POST("bunk/bunkSignIn")
    suspend fun fuelStationSignIn(@Body fuelStationSignInRequest: FuelStationSignInRequest): Response<FuelStationSignInDto>
}