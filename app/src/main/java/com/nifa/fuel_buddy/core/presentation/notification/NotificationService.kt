package com.nifa.fuel_buddy.core.presentation.notification

import android.app.Notification
import android.app.NotificationManager
import android.content.Context

abstract class NotificationService(context : Context) {

    abstract val notification : Notification

    abstract val notificationId : Int

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(){
        notificationManager.notify(notificationId,notification)
    }
}