package com.nifa.fuel_buddy.auth.presentation.feature.accounttype

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.auth.presentation.navigation.AuthNavigation
import com.nifa.fuel_buddy.core.data.datastore.common.PreferenceDataSource
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
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
class ChooseAccountTypeViewModel @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChooseAccountTypeScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ChooseAccountTypeScreenUiState()
    )

    private val _uiEvent = Channel<ChooseAccountTypeScreenUiEvent>()
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

    fun onUiAction(action: ChooseAccountTypeScreenUiAction) {
        when (action) {
            is ChooseAccountTypeScreenUiAction.OnAccountTypeSelected -> updateSelectedAccountTypeUiState(
                action.accountType
            )

            ChooseAccountTypeScreenUiAction.OnContinueCTAButtonClicked -> {

                uiState.value.selectedAccountType?.let { selectedAccountType ->
                    updateAccountTypeInPreference(selectedAccountType)
                    val navigationScreen = AuthNavigation.SignInScreen(selectedAccountType)
                    sendEvent(ChooseAccountTypeScreenUiEvent.NavigateTo(navigationScreen))
                }
            }
        }
    }

    private fun updateAccountTypeInPreference(accountType: AccountType) = viewModelScope.launch {
        preferenceDataSource.setAccountType(accountType)
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

    private fun sendEvent(event: ChooseAccountTypeScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}

data class ChooseAccountTypeScreenUiState(
    val selectedAccountType: AccountType? = null,
    val enableContinueCTA: Boolean = false
)

sealed interface ChooseAccountTypeScreenUiAction {
    data class OnAccountTypeSelected(val accountType: AccountType) : ChooseAccountTypeScreenUiAction
    data object OnContinueCTAButtonClicked : ChooseAccountTypeScreenUiAction
}

sealed interface ChooseAccountTypeScreenUiEvent {
    data class NavigateTo(val navigationScreen: NavigationScreen) : ChooseAccountTypeScreenUiEvent
}