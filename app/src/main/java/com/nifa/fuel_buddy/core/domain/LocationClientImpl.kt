package com.nifa.fuel_buddy.core.domain

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.nifa.fuel_buddy.core.utils.ext.hasLocationPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationClientImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val client: FusedLocationProviderClient
) : LocationClient {

    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long): Flow<Location> {
        return callbackFlow {
            if (!context.hasLocationPermission())
                throw LocationClient.LocationException("No Permission")
            else {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                val isNetworkEnabled =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if (!isGpsEnabled && !isNetworkEnabled) {
                    throw LocationClient.LocationException("GPS is disabled")
                }

                val request = LocationRequest.Builder(interval).build()

                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        super.onLocationResult(result)
                        result.locations.lastOrNull()?.let { location ->
                            launch { send(location) }
                        }
                    }
                }

                client.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )

                awaitClose {
                    client.removeLocationUpdates(locationCallback)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Flow<Location> {
        return callbackFlow {
            val cancellationTokenSource = CancellationTokenSource()

            if (!context.hasLocationPermission()) {
                throw LocationClient.LocationException("NO Permission")
            }

            client.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )
                .addOnSuccessListener {
                    launch { send(it) }
                }.addOnFailureListener {
                    throw it
                }.addOnCanceledListener {
                    throw LocationClient.LocationException("Cancelled")
                }

            awaitClose {
                cancellationTokenSource.cancel()
            }
        }
    }
}