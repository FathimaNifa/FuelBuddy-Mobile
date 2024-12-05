package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
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
class LiveOrderScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LiveOrderScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = LiveOrderScreenUiState()
    )

    private val _uiEvent = Channel<LiveOrderScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val orderList = listOf(
            OrderDetails(
                orderNumber = "22",
                orderId = "1",
                location = "Sakthi Vinayakar Nagar, Injambakkam Chennai, Tamil Nadu 600115",
                awayFrom = "5.5km away",
                userName = "Kannan G",
                totalPrice = "650"
            )
        )

        updateOrderDetails(orderList)
    }

    fun onUiAction(action: LiveOrderScreenUiAction) {
        when (action) {
            LiveOrderScreenUiAction.OnAcceptButtonClicked -> Unit
            is LiveOrderScreenUiAction.OnCardClicked -> {
                sendEvent(
                    LiveOrderScreenUiEvent.NavigateTo(
                        FuelStationNavigation.LiveOrderDetailScreen(
                            orderDetails = action.orderDetails
                        )
                    )
                )
            }

            LiveOrderScreenUiAction.OnDeclineButtonClicked -> Unit
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
    data object OnAcceptButtonClicked : LiveOrderScreenUiAction
    data object OnDeclineButtonClicked : LiveOrderScreenUiAction
}

sealed interface LiveOrderScreenUiEvent {
    data class NavigateTo(val screen: FuelStationNavigation) : LiveOrderScreenUiEvent
}