package com.nifa.fuel_buddy.core.utils.ext

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.nifa.fuel_buddy.core.domain.model.LatLong

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

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

fun Context.openGoogleMaps(latitude: Double, longitude: Double) {
    val query = "google.navigation:q=$latitude,$longitude"
    val gmmIntentUri = Uri.parse(query)
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    startActivity(mapIntent)
}

fun Context.openGoogleMaps(fromLatLong: LatLong, toLatLong: LatLong) {
    val query =
        "https://www.google.com/maps/dir/${fromLatLong.latitude},${fromLatLong.longitude}/${toLatLong.latitude},${toLatLong.longitude}"

    val gmmIntentUri = Uri.parse(query)
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    startActivity(mapIntent)
}