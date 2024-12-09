package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.data.datastore.fuelstation.FuelStationPreferenceDataSource
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.fuelstation.domain.FuelStationRepository
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetCustomerOrdersRequest
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.FuelStationNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveOrderScreenViewModel @Inject constructor(
    private val repository: FuelStationRepository,
    private val preferences: FuelStationPreferenceDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(LiveOrderScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = LiveOrderScreenUiState()
    )

    private val _uiEvent = Channel<LiveOrderScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {

        viewModelScope.launch {

            val bunkId = preferences.fuelStationPreferencesData.first().bunkId
            val bunkToken = preferences.fuelStationPreferencesData.first().bunkToken

            val request = GetCustomerOrdersRequest(
                bunkId = bunkId,
                bunkToken = bunkToken
            )

            repository.getCustomerOrders(request).collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        updateOrderDetails(result.data)
                    }
                }
            }
        }
    }

    fun onUiAction(action: LiveOrderScreenUiAction) {
        when (action) {

            is LiveOrderScreenUiAction.OnAcceptButtonClicked -> {

                // TODO: Add api call here
                sendEvent(
                    LiveOrderScreenUiEvent.OpenGoogleMapApp(
                        latitude = action.latitude,
                        longitude = action.longitude
                    )
                )
            }

            is LiveOrderScreenUiAction.OnCardClicked -> {
                sendEvent(
                    LiveOrderScreenUiEvent.NavigateTo(
                        FuelStationNavigation.LiveOrderDetailScreen(
                            orderDetails = action.orderDetails
                        )
                    )
                )
            }

            is LiveOrderScreenUiAction.OnDeclineButtonClicked -> Unit
        }
    }

    private fun sendEvent(event: LiveOrderScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    private fun updateOrderDetails(orderList: List<OrderDetails>) =
        _uiState.update {
            it.copy(
                orderList = orderList
            )
        }

}

data class LiveOrderScreenUiState(
    val orderList: List<OrderDetails> = emptyList()
)

sealed interface LiveOrderScreenUiAction {
    data class OnCardClicked(val orderDetails: OrderDetails) : LiveOrderScreenUiAction

    data class OnAcceptButtonClicked(
        val orderId: String,
        val latitude: Double,
        val longitude: Double
    ) : LiveOrderScreenUiAction

    data class OnDeclineButtonClicked(val orderId: String) : LiveOrderScreenUiAction
}

sealed interface LiveOrderScreenUiEvent {
    data class NavigateTo(val screen: FuelStationNavigation) : LiveOrderScreenUiEvent
    data class OpenGoogleMapApp(val latitude: Double, val longitude: Double) :
        LiveOrderScreenUiEvent
}