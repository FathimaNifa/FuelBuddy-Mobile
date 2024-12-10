package com.nifa.fuel_buddy.core.presentation.notification

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.nifa.fuel_buddy.R

class UpdateDriverLocationNotification(context: Context) : NotificationService(context) {

    override val notification: Notification =
        NotificationCompat.Builder(context, NotificationChannels.UPDATE_DRIVER_LOCATION.getChannelId(context))
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setContentTitle(context.getString(R.string.out_for_deliver_title))
            .build()

    override val notificationId: Int = 100
}