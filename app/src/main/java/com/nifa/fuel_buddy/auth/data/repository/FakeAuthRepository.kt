package com.nifa.fuel_buddy.auth.data.repository

import com.nifa.fuel_buddy.auth.domain.AuthRepository
import com.nifa.fuel_buddy.auth.domain.model.FuelStation
import com.nifa.fuel_buddy.auth.domain.model.User
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignUpRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignUpRequest
import com.nifa.fuel_buddy.core.data.datastore.fuelstation.FuelStationPreferenceDataSource
import com.nifa.fuel_buddy.core.data.datastore.user.UserPreferenceDataSource
import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeAuthRepository @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val fuelStationPreferenceDataSource: FuelStationPreferenceDataSource
) : AuthRepository {

    override suspend fun userSignIn(userSignInRequest: UserSignInRequest): Flow<Result<User, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            delay(500L)

            emit(
                Result.Success(
                    User(
                        userId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                        userName = "userName",
                        userEmail = userSignInRequest.userEmail,
                        userToken = ""
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun fuelStationSignIn(fuelStationSignInRequest: FuelStationSignInRequest): Flow<Result<FuelStation, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            delay(500L)
            emit(
                Result.Success(
                    FuelStation(
                        bunkId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                        bunkName = "userName",
                        bunkEmail = fuelStationSignInRequest.userEmail,
                        bunkToken = ""
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun setUserPreferences(user: User) {
        with(userPreferenceDataSource) {
            setUserId(user.userId)
            setUserEmail(user.userEmail)
            setUserName(user.userName)
            setUserToken(user.userToken)
        }
    }

    override suspend fun setFuelStationPreferences(fuelStation: FuelStation) {
        with(fuelStationPreferenceDataSource) {
            setFuelStationId(fuelStation.bunkId)
            setFuelStationEmail(fuelStation.bunkEmail)
            setFuelStationName(fuelStation.bunkName)
            setFuelStationToken(fuelStation.bunkToken)
        }
    }

    override suspend fun userSignUp(userSignUpRequest: UserSignUpRequest): Flow<Result<User, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            delay(2000L)
            emit(
                Result.Success(
                    User(
                        userId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                        userName = userSignUpRequest.userName,
                        userEmail = userSignUpRequest.userEmail,
                        userToken = ""
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun fuelStationSignUp(fuelStationSignUpRequest: FuelStationSignUpRequest): Flow<Result<FuelStation, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            delay(500L)
            emit(
                Result.Success(
                    FuelStation(
                        bunkId = "3c301d46-913b-4347-9d69-d5e70ce6c712",
                        bunkName = fuelStationSignUpRequest.bunkName,
                        bunkEmail = fuelStationSignUpRequest.bunkEmail,
                        bunkToken = ""
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }
}