package com.nifa.fuel_buddy.auth.data

import com.nifa.fuel_buddy.auth.domain.AuthRepository
import com.nifa.fuel_buddy.auth.domain.model.FuelStationSignIn
import com.nifa.fuel_buddy.auth.domain.model.UserSignIn
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignUpRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignUpRequest
import com.nifa.fuel_buddy.core.datastore.fuelstation.FuelStationPreferenceDataSource
import com.nifa.fuel_buddy.core.datastore.user.UserPreferenceDataSource
import com.nifa.fuel_buddy.core.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeAuthRepository @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val fuelStationPreferenceDataSource: FuelStationPreferenceDataSource
) : AuthRepository {

    override suspend fun userSignIn(userSignInRequest: UserSignInRequest): Flow<Result<UserSignIn>> {
        return flow {
            emit(Result.Loading)
            delay(500L)

            emit(
                Result.Success(
                    UserSignIn(
                        userId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                        userName = "userName",
                        userEmail = userSignInRequest.userEmail,
                        userToken = ""
                    )
                )
            )
        }
    }

    override suspend fun fuelStationSignIn(fuelStationSignInRequest: FuelStationSignInRequest): Flow<Result<FuelStationSignIn>> {
        return flow {
            emit(Result.Loading)
            delay(500L)
            emit(
                Result.Success(
                    FuelStationSignIn(
                        bunkId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                        bunkName = "userName",
                        bunkEmail = fuelStationSignInRequest.fuelStationEmail,
                        bunkToken = ""
                    )
                )
            )
        }
    }

    override suspend fun setUserPreferences(userSignIn: UserSignIn) {
        with(userPreferenceDataSource) {
            setUserId(userSignIn.userId)
            setUserEmail(userSignIn.userEmail)
            setUserName(userSignIn.userName)
            setUserToken(userSignIn.userToken)
        }
    }

    override suspend fun setFuelStationPreferences(fuelStationSignIn: FuelStationSignIn) {
        with(fuelStationPreferenceDataSource) {
            setFuelStationId(fuelStationSignIn.bunkId)
            setFuelStationEmail(fuelStationSignIn.bunkEmail)
            setFuelStationName(fuelStationSignIn.bunkName)
            setFuelStationToken(fuelStationSignIn.bunkToken)
        }
    }

    override suspend fun userSignUp(userSignUpRequest: UserSignUpRequest): Flow<Result<UserSignIn>> {
        return flow {
            emit(Result.Loading)
            delay(2000L)
            Result.Success(
                UserSignIn(
                    userId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                    userName = userSignUpRequest.userName,
                    userEmail = userSignUpRequest.userEmail,
                    userToken = ""
                )
            )
        }
    }

    override suspend fun fuelStationSignUp(fuelStationSignUpRequest: FuelStationSignUpRequest): Flow<Result<FuelStationSignIn>> {
        return flow {
            emit(Result.Loading)
            delay(500L)
            Result.Success(
                FuelStationSignIn(
                    bunkId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                    bunkName = fuelStationSignUpRequest.bunkName,
                    bunkEmail = fuelStationSignUpRequest.bunkEmail,
                    bunkToken = ""
                )
            )
        }
    }
}