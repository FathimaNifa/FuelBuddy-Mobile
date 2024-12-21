package com.nifa.fuel_buddy.core.domain.location

import android.location.Location
import com.nifa.fuel_buddy.core.domain.model.LatLong
import kotlinx.coroutines.flow.Flow

interface LocationClient {

    fun getLocationUpdates(interval: Long): Flow<Location>

     fun getCurrentLocation() : Flow<LatLong>

    class LocationException(message: String) : Exception(message)
}