package com.nifa.fuel_buddy.core.presentation.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

abstract class NotificationService(context: Context) {

    abstract val notification: NotificationCompat.Builder

    abstract val notificationId: Int

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification() {
        notificationManager.notify(notificationId, notification.build())
    }

    fun updateNotification(content: String) {
        notificationManager.notify(notificationId, notification.setContentTitle(content).build())
    }
}