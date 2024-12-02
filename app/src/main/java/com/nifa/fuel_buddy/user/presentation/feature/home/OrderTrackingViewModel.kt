package com.nifa.fuel_buddy.user.presentation.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.google.android.gms.maps.model.LatLng
import com.nifa.fuel_buddy.core.domain.LocationClient
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.user.domain.UserRepository
import com.nifa.fuel_buddy.user.domain.request.TrackOrderRequest
import com.nifa.fuel_buddy.user.presentation.navigation.UserNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrderTrackingViewModel @Inject constructor(
    private val locationClient: LocationClient,
    private val userRepository: UserRepository,
    saveStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderTrackingUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = OrderTrackingUiState()
    )

    init {
        getCurrentLocationAndUpdateUiState()

        val data = saveStateHandle.toRoute<UserNavigation.OrderTrackingScreen>()

        trackOrderAndUpdateDeliveryPartnerLocationUiState(data.orderId)
    }

    private fun trackOrderAndUpdateDeliveryPartnerLocationUiState(orderId: String) =
        viewModelScope.launch {
            userRepository.trackOrder(TrackOrderRequest(orderId)).collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        Timber.d("Track Order : ${result.data}")
                        updateDeliveryPartnerLocation(result.data)
                    }
                }
            }
        }

    private fun getCurrentLocationAndUpdateUiState() {
        locationClient.getCurrentLocation()
            .catch { it.printStackTrace() }
            .onEach {
                updateCurrentLocationUiState(LatLng(it.latitude, it.longitude))
            }
            .launchIn(viewModelScope)
    }

    private fun updateCurrentLocationUiState(currentLocation: LatLng) =
        _uiState.update {
            it.copy(
                currentLocation = currentLocation
            )
        }

    private fun updateDeliveryPartnerLocation(deliveryPartnerLocation: LatLng) =
        _uiState.update {
            it.copy(
                deliveryPartnerLocation = deliveryPartnerLocation
            )
        }

}

data class OrderTrackingUiState(
    val currentLocation: LatLng? = null,
    val deliveryPartnerLocation: LatLng? = null
)