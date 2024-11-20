package com.nifa.fuel_buddy.auth.data.networkSource

import com.nifa.fuel_buddy.auth.data.model.FuelStationSignInDataDto
import com.nifa.fuel_buddy.auth.data.model.FuelStationSignInDto
import com.nifa.fuel_buddy.auth.data.model.FuelStationSignUpDataDto
import com.nifa.fuel_buddy.auth.data.model.FuelStationSignUpDto
import com.nifa.fuel_buddy.auth.data.model.UserSignInDataDto
import com.nifa.fuel_buddy.auth.data.model.UserSignInDto
import com.nifa.fuel_buddy.auth.data.model.UserSignUpDataDto
import com.nifa.fuel_buddy.auth.data.model.UserSignupDto
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignUpRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignUpRequest
import com.nifa.fuel_buddy.core.domain.Error
import com.nifa.fuel_buddy.core.domain.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeAuthNetworkSource @Inject constructor() : AuthNetworkSource {
    override suspend fun userSignIn(userSignInRequest: UserSignInRequest): Flow<Result<UserSignInDto, Error>> {
        return flow {
            emit(Result.Loading(true))
            delay(500L)
            emit(
                Result.Success(
                    UserSignInDto(
                        statusCode = 200,
                        message = "Success",
                        data = UserSignInDataDto(
                            userId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                            userName = "userName",
                            userEmail = userSignInRequest.userEmail,
                            userToken = ""
                        )
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun fuelStationSignIn(fuelStationSignInRequest: FuelStationSignInRequest): Flow<Result<FuelStationSignInDto, Error>> {
        return flow {
            emit(Result.Loading(true))
            delay(500L)
            emit(
                Result.Success(
                    FuelStationSignInDto(
                        statusCode = 200,
                        message = "Success",
                        data = FuelStationSignInDataDto(
                            bunkId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                            bunkName = "userName",
                            bunkEmail = fuelStationSignInRequest.fuelStationEmail,
                            bunkToken = ""
                        )
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun userSignUp(userSignUpRequest: UserSignUpRequest): Flow<Result<UserSignupDto, Error>> {
        return flow {
            emit(Result.Loading(true))
            delay(2000L)
            emit(
                Result.Success(
                    UserSignupDto(
                        statusCode = 200,
                        message = "Success",
                        data = UserSignUpDataDto(
                            userId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                            userName = userSignUpRequest.userName,
                            userEmail = userSignUpRequest.userEmail,
                            userToken = ""
                        )
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun fuelStationSignUp(fuelStationSignUpRequest: FuelStationSignUpRequest): Flow<Result<FuelStationSignUpDto, Error>> {
        return flow {
            emit(Result.Loading(true))
            delay(500L)
            emit(
                Result.Success(
                    FuelStationSignUpDto(
                        statusCode = 200,
                        message = "Success",
                        data = FuelStationSignUpDataDto(
                            bunkId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                            bunkName = fuelStationSignUpRequest.bunkName,
                            bunkEmail = fuelStationSignUpRequest.bunkEmail,
                            bunkToken = ""
                        )
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }
}