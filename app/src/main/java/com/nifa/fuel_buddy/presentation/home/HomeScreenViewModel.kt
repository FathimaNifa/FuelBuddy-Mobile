package com.nifa.fuel_buddy.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.data.FakeFuelBuddyRepositoryImpl
import com.nifa.fuel_buddy.domain.FuelBuddyRepository
import com.nifa.fuel_buddy.domain.FuelStation
import com.nifa.fuel_buddy.presentation.navigation.NavigationScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeScreenUiState()
    )

    private val _uiEvent = Channel<HomeScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val repo : FuelBuddyRepository = FakeFuelBuddyRepositoryImpl()

    init {
        getFuelStationListAndUpdateInUiState()
    }

    fun onUiAction(action: HomeScreenUiAction) {
        when (action) {
            is HomeScreenUiAction.OnFuelStationCardClicked -> {
                sendEvent(HomeScreenUiEvent.NavigateTo(NavigationScreen.HomeDetailScreen(action.fuelStation)))
            }
        }
    }

    private fun getFuelStationListAndUpdateInUiState() = viewModelScope.launch {
        val fuelStationList = repo.getNearbyFuelStation()
        updateFuelStationListUiState(fuelStationList)
    }

    private fun updateFuelStationListUiState(fuelStationList: List<FuelStation>): Unit =
        _uiState.update {
            it.copy(
                fuelStationList = fuelStationList
            )
        }

    private fun sendEvent(event: HomeScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}

data class HomeScreenUiState(
    val fuelStationList: List<FuelStation> = emptyList()
)

sealed interface HomeScreenUiAction {
    data class OnFuelStationCardClicked(val fuelStation: FuelStation) : HomeScreenUiAction
}

sealed interface HomeScreenUiEvent {
    data class NavigateTo(val navigationScreen: NavigationScreen) : HomeScreenUiEvent
}