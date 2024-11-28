package com.nifa.fuel_buddy.user.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.data.datastore.user.UserPreferenceDataSource
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.user.domain.UserRepository
import com.nifa.fuel_buddy.user.domain.model.FuelStation
import com.nifa.fuel_buddy.user.domain.request.GetNearbyFuelStationRequest
import com.nifa.fuel_buddy.user.presentation.navigation.UserNavigation
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
class HomeScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeScreenUiState()
    )

    private val _uiEvent = Channel<HomeScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getFuelStationListAndUpdateInUiState()
    }

    fun onUiAction(action: HomeScreenUiAction) {
        when (action) {
            is HomeScreenUiAction.OnFuelStationCardClicked -> {
                sendEvent(HomeScreenUiEvent.NavigateTo(UserNavigation.HomeDetailScreen(action.fuelStation)))
            }
        }
    }

    private fun getFuelStationListAndUpdateInUiState() = viewModelScope.launch {
        val request = GetNearbyFuelStationRequest(
            userId = userPreferenceDataSource.userPreferencesData.first().userId,
            latitude = "0",
            longitude = "0"
        )
        userRepository.getNearbyFuelStation(request).collect { result ->
            when (result) {
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> {
                    updateFuelStationListUiState(result.data)
                }
            }
        }
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