package com.nifa.fuel_buddy.fuelstation.presentation.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.presentation.notification.NotificationChannels.UPDATE_DRIVER_LOCATION
import com.nifa.fuel_buddy.core.presentation.notification.NotificationService

class UpdateDriverLocationNotification(context: Context) : NotificationService(context) {

    override val notification: NotificationCompat.Builder =
        NotificationCompat.Builder(context, UPDATE_DRIVER_LOCATION.getChannelId(context))
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(UPDATE_DRIVER_LOCATION.channelImportance)
            .setContentTitle(context.getString(R.string.out_for_deliver_title))
            .setOngoing(true)
            .setAutoCancel(false)

    override val notificationId: Int = 100
}