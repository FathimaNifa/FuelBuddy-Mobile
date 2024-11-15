package com.nifa.fuel_buddy.auth.presentation.dlverification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.auth.util.ValidateDateField
import com.nifa.fuel_buddy.auth.util.ValidateEmptyField
import com.nifa.fuel_buddy.core.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.UiText
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

class DLVerificationScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DLVerificationScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = DLVerificationScreenUiState()
    )

    private val _uiEvent = Channel<DLVerificationScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        observeDLNumberSupportingTextAndUpdateShowDLNumberAsErrorUiState()
        observeDOBSupportingTextAndUpdateShowDOBErrorUiState()
    }

    fun onUiAction(action: DLVerificationScreenUiAction) {
        when (action) {
            DLVerificationScreenUiAction.OnCalendarIconClicked -> Unit

            DLVerificationScreenUiAction.OnVerifyButtonClicked -> {
                validate()
            }

            is DLVerificationScreenUiAction.TypingDLNumber -> {
                updateTypedDLNumberUiState(action.dlNumber)
                updateDLNumberSupportingTextUiState(null)
            }

            is DLVerificationScreenUiAction.TypingDOB -> {
                updateTypedDOBUiState(action.dob)
                updateDOBSupportingTextUiState(null)
            }
        }
    }

    private fun validate() {
        val dlNumber = uiState.value.typedDLNumber
        val dob = uiState.value.typedDOB

        val dlNumberResult =
            ValidateEmptyField.execute(dlNumber, R.string.driving_license_number_not_blank)
        val dobResult = ValidateDateField.execute(dob)

        val hasError = listOf(
            dlNumberResult,
            dobResult
        ).any { !it.isSuccessful }

        if (hasError) {
            updateDLNumberSupportingTextUiState(dlNumberResult.errorMessage)
            updateDOBSupportingTextUiState(dobResult.errorMessage)
            return
        }

//        sendEvent(SignInScreenUiEvent.NavigateAndPopupBackStack(UserNavigation.UserNavGraph))
    }

    private fun observeDOBSupportingTextAndUpdateShowDOBErrorUiState() {
        uiState.map { it.dobSupportingText }
            .distinctUntilChanged()
            .onEach {
                val showDOBAsError = it != null
                updateShowDOBAsErrorUiState(showDOBAsError)
            }.launchIn(viewModelScope)
    }

    private fun observeDLNumberSupportingTextAndUpdateShowDLNumberAsErrorUiState() {
        uiState.map { it.dlNumberSupportingText }
            .distinctUntilChanged()
            .onEach {
                val showDLNumberAsError = it != null
                updateShowDLNumberAsErrorUiState(showDLNumberAsError)
            }.launchIn(viewModelScope)
    }


    private fun sendEvent(event: DLVerificationScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    private fun updateTypedDLNumberUiState(dlNumber: String): Unit =
        _uiState.update {
            it.copy(
                typedDLNumber = dlNumber
            )
        }

    private fun updateTypedDOBUiState(dob: String): Unit =
        _uiState.update {
            it.copy(
                typedDOB = dob
            )
        }

    private fun updateDLNumberSupportingTextUiState(dlNumberSupportingText: UiText?): Unit =
        _uiState.update {
            it.copy(
                dlNumberSupportingText = dlNumberSupportingText
            )
        }

    private fun updateDOBSupportingTextUiState(dobSupportingText: UiText?): Unit =
        _uiState.update {
            it.copy(
                dobSupportingText = dobSupportingText
            )
        }

    private fun updateShowDLNumberAsErrorUiState(showDLNumberAsError: Boolean): Unit =
        _uiState.update {
            it.copy(
                showDLNumberAsError = showDLNumberAsError
            )
        }

    private fun updateShowDOBAsErrorUiState(showDOBAsError: Boolean): Unit =
        _uiState.update {
            it.copy(
                showDOBAsError = showDOBAsError
            )
        }
}

sealed interface DLVerificationScreenUiEvent {
    data class NavigateTo(val navigationScreen: NavigationScreen) : DLVerificationScreenUiEvent
}

sealed interface DLVerificationScreenUiAction {
    data class TypingDLNumber(val dlNumber: String) : DLVerificationScreenUiAction
    data class TypingDOB(val dob: String) : DLVerificationScreenUiAction
    data object OnVerifyButtonClicked : DLVerificationScreenUiAction
    data object OnCalendarIconClicked : DLVerificationScreenUiAction
}

data class DLVerificationScreenUiState(
    val typedDLNumber: String = "",
    val typedDOB: String = "",
    val dlNumberSupportingText: UiText? = null,
    val dobSupportingText: UiText? = null,
    val showDLNumberAsError: Boolean = false,
    val showDOBAsError: Boolean = false,
)