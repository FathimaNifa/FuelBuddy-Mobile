package com.nifa.fuel_buddy.fuelstation.presentation.feature.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.data.datastore.fuelstation.FuelStationPreferenceDataSource
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.FuelStationNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val preferences: FuelStationPreferenceDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = AccountScreenUiState()
    )

    private val _uiEvent = Channel<AccountScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        observeAndUpdateUserEmail()
        observeAndUpdateUserName()
    }

    private fun observeAndUpdateUserEmail() {
        preferences.fuelStationPreferencesData
            .map { it.bunkEmail }
            .distinctUntilChanged()
            .onEach { updateBunkEmailUiState(it) }
            .launchIn(viewModelScope)
    }

    private fun observeAndUpdateUserName() {
        preferences.fuelStationPreferencesData
            .map { it.bunkName }
            .distinctUntilChanged()
            .onEach { updateBunkNameUiState(it) }
            .launchIn(viewModelScope)
    }


    fun onUiAction(action: AccountScreenUiAction) {
        when (action) {
            AccountScreenUiAction.OnChangePasswordClicked -> Unit
            AccountScreenUiAction.OnLogoutButtonClicked -> logoutUser()
            AccountScreenUiAction.OnSupportAndFeedBackClicked -> {
                sendEvent(
                    AccountScreenUiEvent.NavigateTo(
                        FuelStationNavigation.FeedbackScreen
                    )
                )
            }
        }
    }

    private fun updateBunkEmailUiState(bunkEmail: String): Unit =
        _uiState.update {
            it.copy(
                bunkEmail = bunkEmail
            )
        }

    private fun updateBunkNameUiState(bunkName: String): Unit =
        _uiState.update {
            it.copy(
                bunkName = bunkName
            )
        }

    private fun logoutUser() = viewModelScope.launch {
        preferences.clearAll()
    }

    private fun sendEvent(event: AccountScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

}

data class AccountScreenUiState(
    val bunkEmail: String = "",
    val bunkName: String = ""
)

sealed interface AccountScreenUiAction {
    data object OnChangePasswordClicked : AccountScreenUiAction
    data object OnSupportAndFeedBackClicked : AccountScreenUiAction
    data object OnLogoutButtonClicked : AccountScreenUiAction
}
sealed interface AccountScreenUiEvent {
    data class NavigateTo(val screen: FuelStationNavigation) : AccountScreenUiEvent
}