package com.nifa.fuel_buddy.fuelstation.presentation.service

import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.IBinder
import androidx.core.net.toUri
import com.nifa.fuel_buddy.core.domain.LocationClient
import com.nifa.fuel_buddy.core.presentation.MainActivity
import com.nifa.fuel_buddy.core.utils.Deeplink
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
        intent?.let {
            when (intent.action) {
                ACTION_START -> start(it)
                ACTION_STOP -> stop()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(intent: Intent) {

        val bundle = intent.extras ?: throw IllegalArgumentException("Required Key not found")

        val latitude = bundle.getDouble(LATITUDE)
        val longitude = bundle.getDouble(LONGITUDE)
        val orderId = bundle.getString(ORDER_ID) ?: ""

        val data = "${Deeplink.OUT_FOR_DELIVERY_BASE_PATH}/$latitude/$longitude/$orderId"

        Timber.d(data)

        val activityIntent = Intent(this, MainActivity::class.java).apply {
            this.data = data.toUri()
        }
        val pendingIntent = TaskStackBuilder.create(this)?.run {
            addNextIntentWithParentStack(activityIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = UpdateDriverLocationNotification(
            pendingIntent = pendingIntent,
            context = this
        )

        startForeground(
            notification.notificationId,
            notification.notification.build()
        )

        locationClient.getLocationUpdates(1000L)
            .catch { it.printStackTrace() }
            .onEach {
                val request = UpdateDriverLocationRequest(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    orderId = orderId
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
        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"
        const val ORDER_ID = "ORDER_ID"
    }
}