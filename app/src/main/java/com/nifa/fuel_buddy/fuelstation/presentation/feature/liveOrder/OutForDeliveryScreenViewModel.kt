package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.FuelStationNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OutForDeliveryScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = UiState()
    )

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val data = savedStateHandle.toRoute<FuelStationNavigation.OutForDeliveryScreen>()

        updateLatitudeUiState(data.latitude)
        updateLongitudeUiState(data.longitude)
        updateOrderIdUiState(data.orderId)

    }

    fun onUiAction(action: UiAction) {
        when (action) {
            UiAction.OnDeliveredOrderButtonClicked -> {
                // TODO: Add Api call here
            }

            UiAction.OnTakeMeToMapButtonClicked -> {
                sendEvent(
                    UiEvent.OpenGoogleMapApp(
                        latitude = uiState.value.latitude,
                        longitude = uiState.value.longitude
                    )
                )
            }
        }
    }

    private fun sendEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    private fun updateLatitudeUiState(latitude: Double) =
        _uiState.update {
            it.copy(
                latitude = latitude
            )
        }

    private fun updateLongitudeUiState(longitude: Double) =
        _uiState.update {
            it.copy(
                longitude = longitude
            )
        }

    private fun updateOrderIdUiState(orderId: String) =
        _uiState.update {
            it.copy(
                orderId = orderId
            )
        }

    data class UiState(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val orderId: String = ""
    )

    sealed interface UiAction {
        data object OnTakeMeToMapButtonClicked : UiAction
        data object OnDeliveredOrderButtonClicked : UiAction
    }

    sealed interface UiEvent {
        data class OpenGoogleMapApp(val latitude: Double, val longitude: Double) : UiEvent
        data class NavigateTo(val screen: FuelStationNavigation) : UiEvent
    }
}
