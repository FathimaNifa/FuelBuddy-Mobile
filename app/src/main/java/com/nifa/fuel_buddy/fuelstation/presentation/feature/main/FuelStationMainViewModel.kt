package com.nifa.fuel_buddy.fuelstation.presentation.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.bottomNavigation.FuelStationBottomNavigationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class FuelStationMainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FuelStationMainScreenViewModelUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = FuelStationMainScreenViewModelUiState()
    )

    fun onUiAction(action: FuelStationMainScreenViewModelUiAction) {
        when (action) {
            is FuelStationMainScreenViewModelUiAction.OnNavDestinationChanged -> setBottomBarVisibility(
                action.route
            )
        }
    }

    private fun setBottomBarVisibility(route: String) {
        val shouldShowBottomBar = FuelStationBottomNavigationItem.entries.any {
            it.screen::class.qualifiedName == route
        }
        updateShouldShowBottomBarUiState(shouldShowBottomBar)
    }

    private fun updateShouldShowBottomBarUiState(shouldShowBottomBar: Boolean): Unit =
        _uiState.update {
            it.copy(
                shouldShowBottomBar = shouldShowBottomBar
            )
        }


}

data class FuelStationMainScreenViewModelUiState(
    val shouldShowBottomBar: Boolean = true
)

sealed interface FuelStationMainScreenViewModelUiAction {
    data class OnNavDestinationChanged(val route: String) : FuelStationMainScreenViewModelUiAction
}