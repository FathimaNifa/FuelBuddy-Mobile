package com.nifa.fuel_buddy.auth.data.repository

import com.nifa.fuel_buddy.auth.data.model.toFuelStation
import com.nifa.fuel_buddy.auth.data.model.toUser
import com.nifa.fuel_buddy.auth.data.networkSource.AuthNetworkSource
import com.nifa.fuel_buddy.auth.domain.AuthRepository
import com.nifa.fuel_buddy.auth.domain.model.FuelStation
import com.nifa.fuel_buddy.auth.domain.model.User
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignUpRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignUpRequest
import com.nifa.fuel_buddy.auth.domain.model.toFuelStationPreferences
import com.nifa.fuel_buddy.auth.domain.model.toUserPreferences
import com.nifa.fuel_buddy.core.data.datastore.fuelstation.FuelStationPreferenceDataSource
import com.nifa.fuel_buddy.core.data.datastore.user.UserPreferenceDataSource
import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val fuelStationPreferenceDataSource: FuelStationPreferenceDataSource,
    private val authNetworkSource: AuthNetworkSource
) : AuthRepository {

    override suspend fun userSignIn(userSignInRequest: UserSignInRequest): Flow<Result<User, NetworkError>> {
        return authNetworkSource.userSignIn(userSignInRequest).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.toUser())
            }
        }
    }

    override suspend fun fuelStationSignIn(fuelStationSignInRequest: FuelStationSignInRequest): Flow<Result<FuelStation, NetworkError>> {
        return authNetworkSource.fuelStationSignIn(fuelStationSignInRequest).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.toFuelStation())
            }
        }
    }

    override suspend fun setUserPreferences(user: User) {
        userPreferenceDataSource.setUserPreferences(user.toUserPreferences())
    }

    override suspend fun setFuelStationPreferences(fuelStation: FuelStation) {
        fuelStationPreferenceDataSource.setFuelStationPreferences(fuelStation.toFuelStationPreferences())
    }

    override suspend fun userSignUp(userSignUpRequest: UserSignUpRequest): Flow<Result<User, NetworkError>> {
        return authNetworkSource.userSignUp(userSignUpRequest).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.toUser())
            }
        }
    }

    override suspend fun fuelStationSignUp(fuelStationSignUpRequest: FuelStationSignUpRequest): Flow<Result<FuelStation, NetworkError>> {
        return authNetworkSource.fuelStationSignUp(fuelStationSignUpRequest).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.toFuelStation())
            }
        }
    }
}