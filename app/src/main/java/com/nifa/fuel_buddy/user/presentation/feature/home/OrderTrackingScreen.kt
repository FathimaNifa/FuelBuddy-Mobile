package com.nifa.fuel_buddy.user.presentation.feature.home

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.nifa.fuel_buddy.R
import kotlinx.coroutines.launch

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun OrderTrackingScreen(
    modifier: Modifier = Modifier,
    uiState: OrderTrackingUiState
) {

    val mapProperties = MapProperties(
        isMyLocationEnabled = uiState.currentLocation != null
    )
    val cameraPositionState = rememberCameraPositionState()


    GoogleMap(
        modifier = modifier,
        properties = mapProperties,
        cameraPositionState = cameraPositionState
    ) {
        val scope = rememberCoroutineScope()

        val markerState = rememberMarkerState()


        MapEffect(uiState.currentLocation) { map ->

            uiState.currentLocation?.let { location ->
                map.setOnMapLoadedCallback {
                    scope.launch {
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    location.latitude,
                                    location.longitude
                                ),
                                15f
                            )
                        )
                    }
                }
            }
        }

        MapEffect(uiState.deliveryPartnerLocation) {
            uiState.deliveryPartnerLocation?.let {
                markerState.position = it
            }
        }


        MarkerComposable(state = markerState) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_fuel_delivery_indicator),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Preview
@Composable
private fun OrderTrackingScreenPreview() {
    OrderTrackingScreen(
        uiState = OrderTrackingUiState()
    )
}