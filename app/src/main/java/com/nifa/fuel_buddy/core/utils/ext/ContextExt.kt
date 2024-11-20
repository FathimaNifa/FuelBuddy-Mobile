package com.nifa.fuel_buddy.core.utils.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService

fun Context.isConnectedToNetwork(): Boolean {
    return getSystemService<ConnectivityManager>()?.let { connectivityManager ->
        val network             = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)        -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)    -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)    -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)   -> true
            else -> false
        }
    } ?: false
}