package com.nifa.fuel_buddy.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.data.datastore.fuelstation.FuelStationPreferenceDataSource
import com.nifa.fuel_buddy.core.data.datastore.user.UserPreferenceDataSource
import com.nifa.fuel_buddy.core.presentation.navigation.NavGraphs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userPreferenceDataSource: UserPreferenceDataSource,
    fuelStationPreferenceDataSource: FuelStationPreferenceDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = MainUiState()
    )

    init {
        combine(
            userPreferenceDataSource.userPreferencesData,
            fuelStationPreferenceDataSource.fuelStationPreferencesData
        ) { userPref, fuelPref ->

            val navGraphs = when {
                userPref.userId.isNotBlank() -> NavGraphs.UserNavGraph
                fuelPref.bunkId.isNotBlank() -> NavGraphs.FuelStationNavGraph
                else -> NavGraphs.AuthNavGraph
            }

            updateNavGraphUiState(navGraphs)

            delay(500L)
            updateKeepSplashScreenUiState(false)

        }.launchIn(viewModelScope)
    }

    private fun updateNavGraphUiState(navGraphs: NavGraphs): Unit =
        _uiState.update {
            it.copy(
                startDestination = navGraphs
            )
        }

    private fun updateKeepSplashScreenUiState(keepSplashScreen: Boolean): Unit =
        _uiState.update {
            it.copy(
                keepSplashScreen = keepSplashScreen
            )
        }

}

data class MainUiState(
    val startDestination: NavGraphs = NavGraphs.AuthNavGraph,
    val keepSplashScreen: Boolean = true
)