package com.nifa.fuel_buddy.fuelstation.presentation.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.nifa.fuel_buddy.core.domain.LocationClient
import com.nifa.fuel_buddy.fuelstation.domain.FuelStationRepository
import com.nifa.fuel_buddy.fuelstation.domain.model.request.UpdateDriverLocationRequest
import com.nifa.fuel_buddy.fuelstation.presentation.notification.UpdateDriverLocationNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LocationUpdateService : Service() {

    @Inject
    lateinit var locationClient: LocationClient

    @Inject
    lateinit var repository: FuelStationRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {

        val notification = UpdateDriverLocationNotification(this)

        startForeground(
            notification.notificationId,
            notification.notification.build()
        )

        locationClient.getLocationUpdates(1000L)
            .catch { it.printStackTrace() }
            .onEach {
                val request = UpdateDriverLocationRequest(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
                repository.updateDriverLocation(request)
                Timber.d("$it")
            }.launchIn(serviceScope)
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }


    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}