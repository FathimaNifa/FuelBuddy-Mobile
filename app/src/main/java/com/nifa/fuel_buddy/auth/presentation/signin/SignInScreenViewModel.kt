package com.nifa.fuel_buddy.auth.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.auth.presentation.navigation.AuthNavigation
import com.nifa.fuel_buddy.auth.util.ValidateEmail
import com.nifa.fuel_buddy.auth.util.ValidatePassword
import com.nifa.fuel_buddy.core.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.UiText
import com.nifa.fuel_buddy.user.presentation.navigation.UserNavigation
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

class SignInScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignInScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = SignInScreenUiState()
    )

    private val _uiEvent = Channel<SignInScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        observeEmailSupportingTextAndUpdateShowEmailAsErrorUiState()
        observePasswordSupportingTextAndUpdateShowPasswordAsErrorUiState()
    }

    private fun observePasswordSupportingTextAndUpdateShowPasswordAsErrorUiState() {
        uiState.map { it.emailSupportingText }
            .distinctUntilChanged()
            .onEach {
                val showEmailAsError = it != null
                updateShowEmailAsErrorUiState(showEmailAsError)
            }.launchIn(viewModelScope)
    }

    private fun observeEmailSupportingTextAndUpdateShowEmailAsErrorUiState() {
        uiState.map { it.passwordSupportingText }
            .distinctUntilChanged()
            .onEach {
                val showPasswordAsError = it != null
                updateShowPasswordAsErrorUiState(showPasswordAsError)
            }.launchIn(viewModelScope)
    }

    fun onUiAction(action: SignInScreenUiAction) {

        when (action) {
            SignInScreenUiAction.OnForgotPasswordClicked -> {
                sendEvent(SignInScreenUiEvent.NavigateTo(AuthNavigation.ForgotPasswordScreen))
            }

            SignInScreenUiAction.OnSignUpClicked -> {
                sendEvent(SignInScreenUiEvent.NavigateTo(AuthNavigation.ChooseSignUpScreen))
            }

            SignInScreenUiAction.OnPasswordVisibilityButtonClicked -> {
                val maskPassword = uiState.value.maskPassword
                updateMaskPasswordUiState(!maskPassword)
            }

            is SignInScreenUiAction.TypingEmail -> {
                updateTypedEmailUiState(action.email)
                updateEmailSupportingTextUiState(null)
            }

            is SignInScreenUiAction.TypingPassword -> {
                updateTypedPasswordUiState(action.password)
                updatePasswordSupportingTextUiState(null)
            }

            SignInScreenUiAction.OnSignInButtonClicked -> validate()
        }
    }

    private fun validate() {
        val email = uiState.value.typedEmail
        val password = uiState.value.typedPassword

        val emailResult = ValidateEmail.validate(email)
        val passwordResult = ValidatePassword.validate(password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.isSuccessful }

        if (hasError) {
            updateEmailSupportingTextUiState(emailResult.errorMessage)
            updatePasswordSupportingTextUiState(passwordResult.errorMessage)
            return
        }

        sendEvent(SignInScreenUiEvent.NavigateAndPopupBackStack(UserNavigation.UserNavGraph))
    }

    private fun sendEvent(event: SignInScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    private fun updateTypedEmailUiState(typedEmail: String): Unit =
        _uiState.update {
            it.copy(
                typedEmail = typedEmail
            )
        }

    private fun updateTypedPasswordUiState(typedPassword: String): Unit =
        _uiState.update {
            it.copy(
                typedPassword = typedPassword
            )
        }

    private fun updateEmailSupportingTextUiState(emailSupportingText: UiText?): Unit =
        _uiState.update {
            it.copy(
                emailSupportingText = emailSupportingText
            )
        }

    private fun updatePasswordSupportingTextUiState(passwordSupportingText: UiText?): Unit =
        _uiState.update {
            it.copy(
                passwordSupportingText = passwordSupportingText
            )
        }

    private fun updateShowEmailAsErrorUiState(showEmailAsError: Boolean): Unit =
        _uiState.update {
            it.copy(
                showEmailAsError = showEmailAsError
            )
        }

    private fun updateShowPasswordAsErrorUiState(showPasswordAsError: Boolean): Unit =
        _uiState.update {
            it.copy(
                showPasswordAsError = showPasswordAsError
            )
        }

    private fun updateMaskPasswordUiState(maskPassword: Boolean): Unit =
        _uiState.update {
            it.copy(
                maskPassword = maskPassword
            )
        }
}

sealed interface SignInScreenUiEvent {
    data class NavigateTo(val navigationScreen: NavigationScreen) : SignInScreenUiEvent
    data class NavigateAndPopupBackStack(val navigationScreen: NavigationScreen) : SignInScreenUiEvent
}

sealed interface SignInScreenUiAction {
    data class TypingEmail(val email: String) : SignInScreenUiAction
    data class TypingPassword(val password: String) : SignInScreenUiAction
    data object OnPasswordVisibilityButtonClicked : SignInScreenUiAction
    data object OnForgotPasswordClicked : SignInScreenUiAction
    data object OnSignInButtonClicked : SignInScreenUiAction
    data object OnSignUpClicked : SignInScreenUiAction
}

data class SignInScreenUiState(
    val typedEmail: String = "",
    val typedPassword: String = "",
    val emailSupportingText: UiText? = null,
    val passwordSupportingText: UiText? = null,
    val showEmailAsError: Boolean = false,
    val showPasswordAsError: Boolean = false,
    val maskPassword: Boolean = true
)