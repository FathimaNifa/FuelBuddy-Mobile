package com.nifa.fuel_buddy.core.utils.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.content.getSystemService

fun Context.isConnectedToNetwork(): Boolean {
    return getSystemService<ConnectivityManager>()?.let { connectivityManager ->
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } ?: false
}

fun PaddingValues.add(extraPadding: Dp) = PaddingValues(
    start = calculateStartPadding(LayoutDirection.Ltr) + extraPadding,
    end = calculateEndPadding(LayoutDirection.Rtl) + extraPadding,
    top = calculateTopPadding() + extraPadding,
    bottom = calculateBottomPadding() + extraPadding
)