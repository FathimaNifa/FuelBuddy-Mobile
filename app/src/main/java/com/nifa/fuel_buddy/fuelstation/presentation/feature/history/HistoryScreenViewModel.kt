package com.nifa.fuel_buddy.fuelstation.presentation.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.data.datastore.fuelstation.FuelStationPreferenceDataSource
import com.nifa.fuel_buddy.core.domain.util.Result
import com.nifa.fuel_buddy.fuelstation.domain.FuelStationRepository
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderHistoryRequest
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
class HistoryScreenViewModel @Inject constructor(
    private val repository: FuelStationRepository,
    private val preferences: FuelStationPreferenceDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HistoryScreenUiState()
    )

    private val _uiEvent = Channel<HistoryScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {

        viewModelScope.launch {

            val bunkId = preferences.fuelStationPreferencesData.first().bunkId
            val bunkToken = preferences.fuelStationPreferencesData.first().bunkToken

            val request = GetOrderHistoryRequest(
                bunkId = bunkId
            )

            repository.getOrderHistory(request).collect { result ->
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