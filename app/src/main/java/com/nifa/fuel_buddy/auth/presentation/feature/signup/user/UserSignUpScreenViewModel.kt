package com.nifa.fuel_buddy.auth.presentation.feature.signup.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.auth.presentation.navigation.AuthNavigation
import com.nifa.fuel_buddy.auth.util.ValidateConfirmPassword
import com.nifa.fuel_buddy.auth.util.ValidateEmail
import com.nifa.fuel_buddy.auth.util.ValidateEmptyField
import com.nifa.fuel_buddy.auth.util.ValidatePassword
import com.nifa.fuel_buddy.core.presentation.navigation.NavGraphs
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
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

class UserSignUpScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UserSignUpScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = UserSignUpScreenUiState()
    )

    private val _uiEvent = Channel<UserSignUpScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        observeUserNameSupportingTextAndUpdateShowUserNameAsErrorUiState()
        observeEmailSupportingTextAndUpdateShowEmailAsErrorUiState()
        observePasswordSupportingTextAndUpdateShowPasswordAsErrorUiState()
        observeConfirmPasswordSupportingTextAndUpdateShowConfirmPasswordAsErrorUiState()
    }

    private fun observeUserNameSupportingTextAndUpdateShowUserNameAsErrorUiState() {
        uiState.map { it.userNameSupportingText }
            .distinctUntilChanged()
            .onEach {
                val showUserNamAsError = it != null
                updateShowUserNameAsErrorUiState(showUserNamAsError)
            }.launchIn(viewModelScope)
    }

    private fun observeConfirmPasswordSupportingTextAndUpdateShowConfirmPasswordAsErrorUiState() {
        uiState.map { it.emailIdSupportingText }
            .distinctUntilChanged()
            .onEach {
                val showEmailAsError = it != null
                updateShowEmailAsErrorUiState(showEmailAsError)
            }.launchIn(viewModelScope)
    }

    private fun observePasswordSupportingTextAndUpdateShowPasswordAsErrorUiState() {
        uiState.map { it.passwordSupportingText }
            .distinctUntilChanged()
            .onEach {
                val showPasswordAsError = it != null
                updateShowPasswordAsErrorUiState(showPasswordAsError)
            }.launchIn(viewModelScope)
    }

    private fun observeEmailSupportingTextAndUpdateShowEmailAsErrorUiState() {
        uiState.map { it.confirmPasswordSupportingText }
            .distinctUntilChanged()
            .onEach {
                val showConfirmPasswordAsError = it != null
                updateShowConfirmPasswordAsErrorUiState(showConfirmPasswordAsError)
            }.launchIn(viewModelScope)
    }

    fun onUiAction(action: UserSignUpScreenUiAction) {
        when (action) {

            UserSignUpScreenUiAction.OnPasswordVisibilityButtonClicked -> {
                val maskPassword = uiState.value.maskPassword
                updateMaskPasswordUiState(!maskPassword)
            }

            UserSignUpScreenUiAction.OnConfirmPasswordVisibilityButtonClicked -> {
                val maskConfirmPassword = uiState.value.maskConfirmPassword
                updateMaskConfirmPasswordUiState(!maskConfirmPassword)
            }

            UserSignUpScreenUiAction.OnNextButtonClicked -> {
                validate()
            }

            UserSignUpScreenUiAction.OnSignInButtonClicked -> {
                sendEvent(UserSignUpScreenUiEvent.NavigateAndPopupBackStack(NavGraphs.AuthNavGraph))
            }

            is UserSignUpScreenUiAction.TypingUserName -> {
                updateTypedUserNameUiState(action.userName)
                updateUserNameSupportingTextUiState(null)
            }

            is UserSignUpScreenUiAction.TypingEmail -> {
                updateTypedEmailUiState(action.email)
                updateEmailSupportingTextUiState(null)
            }

            is UserSignUpScreenUiAction.TypingPassword -> {
                updateTypedPasswordUiState(action.password)
                updatePasswordSupportingTextUiState(null)
            }

            is UserSignUpScreenUiAction.TypingConfirmPassword -> {
                updateTypedConfirmPasswordUiState(action.confirmPassword)
                updateConfirmPasswordSupportingTextUiState(null)
            }
        }
    }

    private fun validate() {


        val userName = uiState.value.typedUserName
        val email = uiState.value.typedEmailId
        val password = uiState.value.typedPassword
        val confirmPassword = uiState.value.typedConfirmPassword

        val userNameResult = ValidateEmptyField.execute(userName, R.string.user_name_not_blank)
        val emailResult = ValidateEmail.validate(email)
        val passwordResult = ValidatePassword.validate(password)
        val confirmPasswordResult = ValidateConfirmPassword.execute(
            password = password,
            confirmPassword = confirmPassword
        )

        val hasError = listOf(
            userNameResult,
            emailResult,
            passwordResult,
            confirmPasswordResult
        ).any { !it.isSuccessful }

        if (hasError) {
            updateUserNameSupportingTextUiState(userNameResult.errorMessage)
            updateEmailSupportingTextUiState(emailResult.errorMessage)
            updatePasswordSupportingTextUiState(passwordResult.errorMessage)
            updateConfirmPasswordSupportingTextUiState(confirmPasswordResult.errorMessage)
            return
        }

        val navigationScreen = AuthNavigation.DLVerificationScreen(
            userName = userName,
            userEmail = email,
            password = password
        )

        sendEvent(UserSignUpScreenUiEvent.NavigateTo(navigationScreen))

    }

    private fun sendEvent(event: UserSignUpScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    private fun updateTypedUserNameUiState(userName: String): Unit =
        _uiState.update {
            it.copy(
                typedUserName = userName
            )
        }

    private fun updateTypedEmailUiState(typedEmail: String): Unit =
        _uiState.update {
            it.copy(
                typedEmailId = typedEmail
            )
        }

    private fun updateTypedPasswordUiState(typedPassword: String): Unit =
        _uiState.update {
            it.copy(
                typedPassword = typedPassword
            )
        }

    private fun updateTypedConfirmPasswordUiState(typedConfirmPassword: String): Unit =
        _uiState.update {
            it.copy(
                typedConfirmPassword = typedConfirmPassword
            )
        }

    private fun updateUserNameSupportingTextUiState(userNameSupportingText: UiText?): Unit =
        _uiState.update {
            it.copy(
                userNameSupportingText = userNameSupportingText
            )
        }

    private fun updateEmailSupportingTextUiState(emailSupportingText: UiText?): Unit =
        _uiState.update {
            it.copy(
                emailIdSupportingText = emailSupportingText
            )
        }

    private fun updatePasswordSupportingTextUiState(passwordSupportingText: UiText?): Unit =
        _uiState.update {
            it.copy(
                passwordSupportingText = passwordSupportingText
            )
        }

    private fun updateConfirmPasswordSupportingTextUiState(confirmPasswordSupportingText: UiText?): Unit =
        _uiState.update {
            it.copy(
                confirmPasswordSupportingText = confirmPasswordSupportingText
            )
        }

    private fun updateShowUserNameAsErrorUiState(showUserNameAsError: Boolean): Unit =
        _uiState.update {
            it.copy(
                showUserNameAsError = showUserNameAsError
            )
        }

    private fun updateShowEmailAsErrorUiState(showEmailAsError: Boolean): Unit =
        _uiState.update {
            it.copy(
                showEmailIdAsError = showEmailAsError
            )
        }

    private fun updateShowPasswordAsErrorUiState(showPasswordAsError: Boolean): Unit =
        _uiState.update {
            it.copy(
                showPasswordAsError = showPasswordAsError
            )
        }

    private fun updateShowConfirmPasswordAsErrorUiState(showConfirmPasswordAsError: Boolean): Unit =
        _uiState.update {
            it.copy(
                showConfirmPasswordAsError = showConfirmPasswordAsError
            )
        }

    private fun updateMaskPasswordUiState(maskPassword: Boolean): Unit =
        _uiState.update {
            it.copy(
                maskPassword = maskPassword
            )
        }

    private fun updateMaskConfirmPasswordUiState(maskConfirmPassword: Boolean): Unit =
        _uiState.update {
            it.copy(
                maskConfirmPassword = maskConfirmPassword
            )
        }

}

sealed interface UserSignUpScreenUiAction {
    data class TypingUserName(val userName: String) : UserSignUpScreenUiAction
    data class TypingEmail(val email: String) : UserSignUpScreenUiAction
    data class TypingPassword(val password: String) : UserSignUpScreenUiAction
    data class TypingConfirmPassword(val confirmPassword: String) : UserSignUpScreenUiAction
    data object OnPasswordVisibilityButtonClicked : UserSignUpScreenUiAction
    data object OnConfirmPasswordVisibilityButtonClicked : UserSignUpScreenUiAction
    data object OnSignInButtonClicked : UserSignUpScreenUiAction
    data object OnNextButtonClicked : UserSignUpScreenUiAction
}

data class UserSignUpScreenUiState(
    val typedUserName: String = "",
    val typedEmailId: String = "",
    val typedPassword: String = "",
    val typedConfirmPassword: String = "",
    val userNameSupportingText: UiText? = null,
    val emailIdSupportingText: UiText? = null,
    val passwordSupportingText: UiText? = null,
    val confirmPasswordSupportingText: UiText? = null,
    val showUserNameAsError: Boolean = false,
    val showEmailIdAsError: Boolean = false,
    val showPasswordAsError: Boolean = false,
    val showConfirmPasswordAsError: Boolean = false,
    val maskPassword: Boolean = true,
    val maskConfirmPassword: Boolean = true
)

sealed interface UserSignUpScreenUiEvent {
    data class NavigateAndPopupBackStack(val navigationScreen: NavigationScreen) :
        UserSignUpScreenUiEvent

    data class NavigateTo(val navigationScreen: NavigationScreen) : UserSignUpScreenUiEvent
}