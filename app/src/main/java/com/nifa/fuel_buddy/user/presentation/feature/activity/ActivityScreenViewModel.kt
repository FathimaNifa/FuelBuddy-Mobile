package com.nifa.fuel_buddy.user.presentation.feature.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.user.domain.UserRepository
import com.nifa.fuel_buddy.user.domain.model.FuelOrderHistory
import com.nifa.fuel_buddy.user.presentation.navigation.UserNavigation
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
class ActivityScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ActivityScreenUiState()
    )

    private val _uiEvent = Channel<ActivityScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        getFuelOrderHistoryListAndUpdateInUiState()
    }

    private fun getFuelOrderHistoryListAndUpdateInUiState() = viewModelScope.launch {
        userRepository.getFuelOrderHistory().collect { result ->
            when (result) {
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> {
                    updateFuelOrderHistoryListUiState(result.data)
                }
            }
        }

    }

    fun onUiAction(action: ActivityScreenUiAction) {
        when (action) {
            is ActivityScreenUiAction.OnActivityCardClicked -> {
                val navigation = UserNavigation.ActivityDetailScreen(
                    fuelStationName = action.bunkName,
                    orderId = action.orderId,
                    deliveryCharge = action.deliveryCharge
                )
                sendEvent(
                    ActivityScreenUiEvent.NavigateTo(
                        navigation
                    )
                )
            }
        }
    }

    private fun updateFuelOrderHistoryListUiState(fuelOrderHistoryList: List<FuelOrderHistory>): Unit =
        _uiState.update {
            it.copy(
                fuelOrderHistoryList = fuelOrderHistoryList
            )
        }

    private fun sendEvent(event: ActivityScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

}

data class ActivityScreenUiState(
    val fuelOrderHistoryList: List<FuelOrderHistory> = emptyList()
)

sealed interface ActivityScreenUiAction {
    data class OnActivityCardClicked(
        val bunkName : String,
        val deliveryCharge : Int,
        val orderId: String
    ) : ActivityScreenUiAction
}

sealed interface ActivityScreenUiEvent {
    data class NavigateTo(val navigationScreen: NavigationScreen) : ActivityScreenUiEvent
}