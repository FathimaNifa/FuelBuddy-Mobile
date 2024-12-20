package com.nifa.fuel_buddy.auth.presentation.feature.signup.fuelstation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.auth.domain.AuthRepository
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignUpRequest
import com.nifa.fuel_buddy.auth.util.ValidateConfirmPassword
import com.nifa.fuel_buddy.auth.util.ValidateEmail
import com.nifa.fuel_buddy.auth.util.ValidateEmptyField
import com.nifa.fuel_buddy.auth.util.ValidatePassword
import com.nifa.fuel_buddy.core.domain.util.Result
import com.nifa.fuel_buddy.core.presentation.navigation.NavGraphs
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.UiText
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
class FuelStationSignUpScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FuelStationSignUpScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = FuelStationSignUpScreenUiState()
    )

    private val _uiEvent = Channel<FuelStationSignUpScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        observeUserNameSupportingTextAndUpdateShowUserNameAsErrorUiState()
        observeEmailSupportingTextAndUpdateShowEmailAsErrorUiState()
        observePasswordSupportingTextAndUpdateShowPasswordAsErrorUiState()
        observeConfirmPasswordSupportingTextAndUpdateShowConfirmPasswordAsErrorUiState()
        observeRegisterNumberSupportingTextAndUpdateShowRegisterNumberAsErrorUiState()
    }

    fun onUiAction(action: FuelStationSignUpScreenUiAction) {
        when (action) {

            FuelStationSignUpScreenUiAction.OnPasswordVisibilityButtonClicked -> {
                val maskPassword = uiState.value.maskPassword
                updateMaskPasswordUiState(!maskPassword)
            }

            FuelStationSignUpScreenUiAction.OnConfirmPasswordVisibilityButtonClicked -> {
                val maskConfirmPassword = uiState.value.maskConfirmPassword
                updateMaskConfirmPasswordUiState(!maskConfirmPassword)
            }

            FuelStationSignUpScreenUiAction.OnSignInButtonClicked -> {
                sendEvent(FuelStationSignUpScreenUiEvent.NavigateAndPopupBackStack(NavGraphs.AuthNavGraph))
            }

            FuelStationSignUpScreenUiAction.OnSignUpButtonClicked -> {
                validate()
            }

            is FuelStationSignUpScreenUiAction.TypingUserName -> {
                updateTypedUserNameUiState(action.userName)
                updateUserNameSupportingTextUiState(null)
            }

            is FuelStationSignUpScreenUiAction.TypingEmail -> {
                updateTypedEmailUiState(action.email)
                updateEmailSupportingTextUiState(null)
            }

            is FuelStationSignUpScreenUiAction.TypingPassword -> {
                updateTypedPasswordUiState(action.password)
                updatePasswordSupportingTextUiState(null)
            }

            is FuelStationSignUpScreenUiAction.TypingConfirmPassword -> {
                updateTypedConfirmPasswordUiState(action.confirmPassword)
                updateConfirmPasswordSupportingTextUiState(null)
            }

            is FuelStationSignUpScreenUiAction.TypingRegisterNumber -> {
                updateTypedRegisterNumberUiState(action.registerNumber)
                updateRegisterNumberSupportingTextUiState(null)
            }
        }
    }

    private fun validate() {

        val userName = uiState.value.typedUserName
        val email = uiState.value.typedEmailId
        val password = uiState.value.typedPassword
        val confirmPassword = uiState.value.typedConfirmPassword
        val registerNumber = uiState.value.typedRegisterNumber

        val userNameResult = ValidateEmptyField.execute(userName, R.string.user_name_not_blank)
        val emailResult = ValidateEmail.validate(email)
        val passwordResult = ValidatePassword.validate(password)
        val confirmPasswordResult = ValidateConfirmPassword.execute(
            password = password,
            confirmPassword = confirmPassword
        )
        val registerNumberResult =
            ValidateEmptyField.execute(registerNumber, R.string.registration_number_not_blank)

        val hasError = listOf(
            userNameResult,
            emailResult,
            passwordResult,
            confirmPasswordResult,
            registerNumberResult
        ).any { !it.isSuccessful }

        if (hasError) {
            updateUserNameSupportingTextUiState(userNameResult.errorMessage)
            updateEmailSupportingTextUiState(emailResult.errorMessage)
            updatePasswordSupportingTextUiState(passwordResult.errorMessage)
            updateConfirmPasswordSupportingTextUiState(confirmPasswordResult.errorMessage)
            updateRegisterNumberSupportingTextUiState(registerNumberResult.errorMessage)
            return
        }

        val fuelStationSignUpRequest = FuelStationSignUpRequest(
            bunkName = userName,
            bunkEmail = email,
            registrationNumber = registerNumber,
            password = password
        )

        signUp(fuelStationSignUpRequest)
    }

    private fun signUp(fuelStationSignUpRequest: FuelStationSignUpRequest) = viewModelScope.launch {
        with(authRepository) {
            fuelStationSignUp(fuelStationSignUpRequest).collect { result ->
                when (result) {
                    is Result.Error -> {
                        sendEvent(FuelStationSignUpScreenUiEvent.ShowErrorSnackBar(result.error.message))
                    }

                    is Result.Loading -> updateIsLoadingUiState(result.isLoading)
                    is Result.Success -> {
                        val data = result.data
                        setFuelStationPreferences(data)
                    }
                }
            }
        }
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

    private fun observeRegisterNumberSupportingTextAndUpdateShowRegisterNumberAsErrorUiState() {
        uiState.map { it.registerNumberSupportingUiText }
            .distinctUntilChanged()
            .onEach {
                val showRegisterNumberAsError = it != null
                updateRegisterNumberAsErrorUiState(showRegisterNumberAsError)
            }.launchIn(viewModelScope)
    }

    private fun sendEvent(event: FuelStationSignUpScreenUiEvent) = viewModelScope.launch {
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

    private fun updateTypedRegisterNumberUiState(typedRegisterNumber: String): Unit =
        _uiState.update {
            it.copy(
                typedRegisterNumber = typedRegisterNumber
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

    private fun updateRegisterNumberSupportingTextUiState(registerNumberSupportingUiText: UiText?): Unit =
        _uiState.update {
            it.copy(
                registerNumberSupportingUiText = registerNumberSupportingUiText
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

    private fun updateRegisterNumberAsErrorUiState(showRegistrationNumberAsError: Boolean): Unit =
        _uiState.update {
            it.copy(
                showRegistrationNumberAsError = showRegistrationNumberAsError
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

    private fun updateIsLoadingUiState(isLoading: Boolean): Unit =
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
}


data class FuelStationSignUpScreenUiState(
    val typedUserName: String = "",
    val typedEmailId: String = "",
    val typedPassword: String = "",
    val typedConfirmPassword: String = "",
    val typedRegisterNumber: String = "",
    val userNameSupportingText: UiText? = null,
    val emailIdSupportingText: UiText? = null,
    val passwordSupportingText: UiText? = null,
    val confirmPasswordSupportingText: UiText? = null,
    val registerNumberSupportingUiText: UiText? = null,
    val showUserNameAsError: Boolean = false,
    val showEmailIdAsError: Boolean = false,
    val showPasswordAsError: Boolean = false,
    val showConfirmPasswordAsError: Boolean = false,
    val showRegistrationNumberAsError: Boolean = false,
    val maskPassword: Boolean = true,
    val maskConfirmPassword: Boolean = true,
    val isLoading: Boolean = false
)

sealed interface FuelStationSignUpScreenUiAction {
    data class TypingUserName(val userName: String) : FuelStationSignUpScreenUiAction
    data class TypingEmail(val email: String) : FuelStationSignUpScreenUiAction
    data class TypingPassword(val password: String) : FuelStationSignUpScreenUiAction
    data class TypingConfirmPassword(val confirmPassword: String) : FuelStationSignUpScreenUiAction
    data class TypingRegisterNumber(val registerNumber: String) : FuelStationSignUpScreenUiAction
    data object OnPasswordVisibilityButtonClicked : FuelStationSignUpScreenUiAction
    data object OnConfirmPasswordVisibilityButtonClicked : FuelStationSignUpScreenUiAction
    data object OnSignInButtonClicked : FuelStationSignUpScreenUiAction
    data object OnSignUpButtonClicked : FuelStationSignUpScreenUiAction
}

sealed interface FuelStationSignUpScreenUiEvent {
    data class NavigateAndPopupBackStack(val navigationScreen: NavigationScreen) :
        FuelStationSignUpScreenUiEvent

    data class ShowErrorSnackBar(val errorMessage: String) : FuelStationSignUpScreenUiEvent

}