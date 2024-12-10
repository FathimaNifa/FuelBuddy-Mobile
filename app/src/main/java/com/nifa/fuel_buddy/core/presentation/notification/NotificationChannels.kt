package com.nifa.fuel_buddy.core.presentation.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.annotation.StringRes
import com.nifa.fuel_buddy.R

enum class NotificationChannels(
    @StringRes
    val channelId: Int,
    @StringRes
    val channelName: Int,
    val channelImportance: Int
) {

    UPDATE_DRIVER_LOCATION(
        channelId = R.string.update_driver_location_channel_id,
        channelName = R.string.update_driver_location_channel_name,
        channelImportance = NotificationManager.IMPORTANCE_DEFAULT
    );


    fun getNotificationChannel(context : Context) : NotificationChannel =
        NotificationChannel(
            context.getString(channelId),
            context.getString(channelName),
            channelImportance
        )

    fun getChannelId(context: Context) = context.getString(channelId)

}