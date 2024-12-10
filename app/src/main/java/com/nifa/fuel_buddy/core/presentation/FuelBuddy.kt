package com.nifa.fuel_buddy.core.presentation

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import com.nifa.fuel_buddy.core.presentation.notification.NotificationChannels
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class FuelBuddy : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        setupNotificationChannel()
    }

    private fun setupNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannels(
            NotificationChannels.entries.map {
                it.getNotificationChannel(applicationContext)
            }
        )
    }
}