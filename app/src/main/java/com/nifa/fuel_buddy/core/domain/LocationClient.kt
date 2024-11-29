package com.nifa.fuel_buddy.core.domain

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {

    fun getLocationUpdates(interval: Long): Flow<Location>

     fun getCurrentLocation() : Flow<Location>

    class LocationException(message: String) : Exception(message)
}