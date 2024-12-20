package com.nifa.fuel_buddy.core.data.location

import android.location.Location
import com.nifa.fuel_buddy.auth.presentation.feature.accounttype.AccountType
import com.nifa.fuel_buddy.core.data.datastore.common.PreferenceDataSource
import com.nifa.fuel_buddy.core.domain.location.LocationClient
import com.nifa.fuel_buddy.core.domain.model.LatLong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeLocationClientImpl @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
) : LocationClient {

    override fun getLocationUpdates(interval: Long): Flow<Location> {
        return emptyFlow()
    }

    override fun getCurrentLocation(): Flow<LatLong> =
        preferenceDataSource.accountTypeFlow.map { accountType ->
            val location = when (accountType) {
                AccountType.USER -> USER_LOCATION
                AccountType.FUEL_STATION -> DELIVERY_PARTNER_LOCATION
                null -> LatLong(0.0, 0.0)
            }
            location
        }


    companion object {
        val USER_LOCATION = LatLong(
            latitude = 13.0103349,
            longitude = 80.2292406
        )
        val DELIVERY_PARTNER_LOCATION = LatLong(
            latitude = 13.0079438,
            longitude = 80.2416342
        )
    }
}