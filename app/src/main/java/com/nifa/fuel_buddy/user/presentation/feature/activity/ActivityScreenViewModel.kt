package com.nifa.fuel_buddy.user.presentation.feature.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.user.domain.FuelBuddyUserRepository
import com.nifa.fuel_buddy.user.domain.FuelOrderHistory
import com.nifa.fuel_buddy.user.domain.FuelStation
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
    private val fuelBuddyUserRepository: FuelBuddyUserRepository
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
        val fuelOrderHistoryList = fuelBuddyUserRepository.getFuelOrderHistory()
        updateFuelOrderHistoryListUiState(fuelOrderHistoryList)
    }

    fun onUiAction(action: ActivityScreenUiAction) {
        when (action) {
            is ActivityScreenUiAction.OnActivityCardClicked -> {
                sendEvent(
                    ActivityScreenUiEvent.NavigateTo(
                        UserNavigation.ActivityDetailScreen(action.fuelStation)
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
    data class OnActivityCardClicked(val fuelStation: FuelStation) : ActivityScreenUiAction
}

sealed interface ActivityScreenUiEvent {
    data class NavigateTo(val navigationScreen: NavigationScreen) : ActivityScreenUiEvent
}