package com.nifa.fuel_buddy.core.domain

import com.nifa.fuel_buddy.core.data.datastore.fuelstation.FuelStationPreferenceDataSource
import com.nifa.fuel_buddy.core.data.datastore.user.UserPreferenceDataSource
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response

class JwtInterceptor(
    private val fuelStationPreferences: FuelStationPreferenceDataSource,
    private val userPreferences: UserPreferenceDataSource
) : Interceptor {

    private var bearerToken = ""

    init {
        CustomScope.getApplicationScope().launch {
            combine(
                userPreferences.userPreferencesData,
                fuelStationPreferences.fuelStationPreferencesData
            ) { userPref, fuelPref ->
                val token = when {
                    userPref.userId.isNotBlank() -> userPref.userToken
                    fuelPref.bunkId.isNotBlank() -> fuelPref.bunkId
                    else -> ""
                }
                bearerToken = "Bearer $token"
            }.launchIn(this)

        }
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader(NetworkConstant.AUTHORIZATION, bearerToken)
                .build()
        )
    }
}