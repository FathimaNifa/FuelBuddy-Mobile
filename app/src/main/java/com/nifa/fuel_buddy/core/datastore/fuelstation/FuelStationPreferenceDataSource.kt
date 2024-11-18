package com.nifa.fuel_buddy.core.datastore.fuelstation

import kotlinx.coroutines.flow.Flow

interface FuelStationPreferenceDataSource {

    val fuelStationPreferencesData: Flow<FuelStationPreferences>

    suspend fun setFuelStationEmail(emailId: String)

    suspend fun setFuelStationId(id: String)

    suspend fun setFuelStationName(name: String)

    suspend fun setFuelStationToken(token: String)

    suspend fun clearAll()
}