package com.nifa.fuel_buddy.fuelstation.presentation.signup.choosesingup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.navigation.NavigationScreen
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.AuthNavigation
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

class ChooseSignUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChooseSignUpScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ChooseSignUpScreenUiState()
    )

    private val _uiEvent = Channel<ChooseSignUpScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        observeSelectedAccountTypeAndUpdateEnableContinueCTAUiState()
    }

    private fun observeSelectedAccountTypeAndUpdateEnableContinueCTAUiState() {
        uiState.map { it.selectedAccountType }
            .distinctUntilChanged()
            .onEach {
                val enableContinueCTA = it != null
                updateEnableContinueCTAUiState(enableContinueCTA)
            }.launchIn(viewModelScope)
    }

    fun onUiAction(action: ChooseSignUpScreenUiAction) {
        when (action) {
            is ChooseSignUpScreenUiAction.OnAccountTypeSelected -> updateSelectedAccountTypeUiState(
                action.accountType
            )

            ChooseSignUpScreenUiAction.OnContinueCTAButtonClicked -> {

                uiState.value.selectedAccountType?.let { selectedAccountType ->
                    val navigationScreen = when (selectedAccountType) {
                        AccountType.USER -> AuthNavigation.UserSignUpScreen
                        AccountType.FUEL_STATION -> AuthNavigation.FuelStationSignUpScreen
                    }
                    sendEvent(ChooseSignUpScreenUiEvent.NavigateTo(navigationScreen))
                }
            }
        }
    }

    private fun updateSelectedAccountTypeUiState(selectedAccountType: AccountType?): Unit =
        _uiState.update {
            it.copy(
                selectedAccountType = selectedAccountType
            )
        }

    private fun updateEnableContinueCTAUiState(enableContinueCTA: Boolean): Unit =
        _uiState.update {
            it.copy(
                enableContinueCTA = enableContinueCTA
            )
        }

    private fun sendEvent(event: ChooseSignUpScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}

data class ChooseSignUpScreenUiState(
    val selectedAccountType: AccountType? = null,
    val enableContinueCTA: Boolean = false
)

sealed interface ChooseSignUpScreenUiAction {
    data class OnAccountTypeSelected(val accountType: AccountType) : ChooseSignUpScreenUiAction
    data object OnContinueCTAButtonClicked : ChooseSignUpScreenUiAction
}

sealed interface ChooseSignUpScreenUiEvent {
    data class NavigateTo(val navigationScreen: NavigationScreen) : ChooseSignUpScreenUiEvent
}