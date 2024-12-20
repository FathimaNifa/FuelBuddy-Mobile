package com.nifa.fuel_buddy.auth.presentation.feature.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nifa.fuel_buddy.auth.domain.AuthRepository
import com.nifa.fuel_buddy.auth.domain.model.User
import com.nifa.fuel_buddy.auth.domain.model.request.FuelStationSignInRequest
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignInRequest
import com.nifa.fuel_buddy.auth.presentation.feature.accounttype.AccountType
import com.nifa.fuel_buddy.auth.presentation.navigation.AuthNavigation
import com.nifa.fuel_buddy.auth.util.ValidateEmail
import com.nifa.fuel_buddy.auth.util.ValidatePassword
import com.nifa.fuel_buddy.core.domain.Result
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = SignInScreenUiState()
    )

    private val _uiEvent = Channel<SignInScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getAccountTypeAndUpdateUiState()
        observeEmailSupportingTextAndUpdateShowEmailAsErrorUiState()
        observePasswordSupportingTextAndUpdateShowPasswordAsErrorUiState()
        observeAccountTypeAndShowSignupButton()
    }

    private fun observeAccountTypeAndShowSignupButton() {
        uiState.map { it.accountType }
            .distinctUntilChanged()
            .onEach { accountType ->
                val shouldShowSignup = when (accountType) {
                    AccountType.USER -> true
                    AccountType.FUEL_STATION -> false
                }
                updateShouldShowSignupUiState(shouldShowSignup)
            }.launchIn(viewModelScope)
    }

    private fun getAccountTypeAndUpdateUiState() {
        val data = savedStateHandle.toRoute<AuthNavigation.SignInScreen>()
        updateAccountTypeUiState(data.accountType)
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
                val navigationScreen = AuthNavigation.UserSignUpScreen
                sendEvent(SignInScreenUiEvent.NavigateTo(navigationScreen))
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

        val accountType = uiState.value.accountType

        when (accountType) {
            AccountType.USER -> signInUser(
                userEmail = email,
                password = password
            )

            AccountType.FUEL_STATION -> signInFuelStation(
                fuelStationEmail = email,
                password = password
            )
        }
    }

    private fun signInUser(userEmail: String, password: String) = viewModelScope.launch {

        val request = UserSignInRequest(
            userEmail = userEmail,
            password = password
        )
        authRepository.userSignIn(request).collect { result ->
            when (result) {
                is Result.Error -> {
                    Timber.d("${result.error}")
                    sendEvent(SignInScreenUiEvent.ShowErrorSnackBar(result.error.message))
                }

                is Result.Loading -> {
                    updateIsLoadingUiState(result.isLoading)
                }

                is Result.Success -> {
                    setPref(result.data)
                    sendEvent(
                        SignInScreenUiEvent.NavigateAndPopupBackStack(NavGraphs.UserNavGraph)
                    )
                }
            }
        }
    }

    private fun setPref(user: User) = viewModelScope.launch {
        authRepository.setUserPreferences(user)
    }

    private fun signInFuelStation(fuelStationEmail: String, password: String) =
        viewModelScope.launch {
            val request = FuelStationSignInRequest(
                userEmail = fuelStationEmail,
                password = password
            )
            authRepository.fuelStationSignIn(request).collect { result ->
                when (result) {
                    is Result.Error -> {
                        Timber.d("${result.error}")
                        sendEvent(SignInScreenUiEvent.ShowErrorSnackBar(result.error.message))
                    }

                    is Result.Loading -> {
                        updateIsLoadingUiState(isLoading = result.isLoading)
                    }

                    is Result.Success -> {
                        authRepository.setFuelStationPreferences(result.data)
                        sendEvent(
                            SignInScreenUiEvent.NavigateAndPopupBackStack(NavGraphs.FuelStationNavGraph)
                        )
                    }
                }
            }
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

    private fun updateAccountTypeUiState(accountType: AccountType): Unit =
        _uiState.update {
            it.copy(
                accountType = accountType
            )
        }

    private fun updateIsLoadingUiState(isLoading: Boolean): Unit =
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }

    private fun updateShouldShowSignupUiState(shouldShowSignup: Boolean): Unit =
        _uiState.update {
            it.copy(
                shouldShowSignup = shouldShowSignup
            )
        }
}

sealed interface SignInScreenUiEvent {
    data class NavigateTo(val navigationScreen: NavigationScreen) : SignInScreenUiEvent
    data class NavigateAndPopupBackStack(val navigationScreen: NavigationScreen) :
        SignInScreenUiEvent

    data class ShowErrorSnackBar(val errorMessage: String) : SignInScreenUiEvent
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
    val maskPassword: Boolean = true,
    val accountType: AccountType = AccountType.USER,
    val isLoading: Boolean = false,
    val shouldShowSignup: Boolean = true
)