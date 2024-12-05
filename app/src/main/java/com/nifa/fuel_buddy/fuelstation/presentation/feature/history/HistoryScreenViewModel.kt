package com.nifa.fuel_buddy.fuelstation.presentation.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderStatus
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
class HistoryScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HistoryScreenUiState()
    )

    private val _uiEvent = Channel<HistoryScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        val orderList = listOf(
            OrderDetails(
                orderNumber = "22",
                orderId = "1",
                location = "Sakthi Vinayakar Nagar, Injambakkam Chennai, Tamil Nadu 600115",
                awayFrom = "5.5km away",
                userName = "Kannan G",
                totalPrice = "650",
                deliveryCharge = "20",
                orderStatus = OrderStatus.DELIVERED,
                dateAndTime = "20 Oct | 1.41 pm"
            )
        )

        updateOrderDetails(orderList)
    }

    fun onUiAction(action: HistoryScreenUiAction) {
        when (action) {
            is HistoryScreenUiAction.OnCardClicked -> {
                sendEvent(
                    HistoryScreenUiEvent.NavigateTo(
                        FuelStationNavigation.HistoryDetailScreen(
                            action.orderDetails
                        )
                    )
                )
            }
        }
    }

    private fun sendEvent(event: HistoryScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    private fun updateOrderDetails(orderList: List<OrderDetails>) =
        _uiState.update {
            it.copy(
                orderList = orderList
            )
        }
}

data class HistoryScreenUiState(
    val orderList: List<OrderDetails> = emptyList()
)

sealed interface HistoryScreenUiAction {
    data class OnCardClicked(val orderDetails: OrderDetails) : HistoryScreenUiAction
}

sealed interface HistoryScreenUiEvent {
    data class NavigateTo(val screen: FuelStationNavigation) : HistoryScreenUiEvent
}