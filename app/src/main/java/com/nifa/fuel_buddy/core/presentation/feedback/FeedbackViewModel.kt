package com.nifa.fuel_buddy.core.presentation.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.auth.presentation.feature.accounttype.AccountType
import com.nifa.fuel_buddy.core.data.datastore.common.PreferenceDataSource
import com.nifa.fuel_buddy.core.domain.CommonRepository
import com.nifa.fuel_buddy.core.domain.model.request.SubmitFeedbackRequest
import com.nifa.fuel_buddy.core.domain.util.Result
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.FuelStationNavigation
import com.nifa.fuel_buddy.user.presentation.navigation.UserNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val commonRepository: CommonRepository,
    private val preferenceDataSource: PreferenceDataSource
) : ViewModel() {

    companion object {
        const val MAX_TEXT_COUNT = 500
    }

    private val _uiState = MutableStateFlow(FeedbackUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<FeedbackUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        observeTextChangesAndUpdateCurrentTextCount()
        observeTextAndUpdateEnableSubmitCTA()
        observePrefAndUpdateAccountTypeUiState()
    }

    private fun observePrefAndUpdateAccountTypeUiState() {
        preferenceDataSource.accountTypeFlow
            .onEach { accountType ->
                accountType?.let {
                    updateAccountType(accountType)
                }
            }.launchIn(viewModelScope)
    }


    private fun observeTextChangesAndUpdateCurrentTextCount() {
        uiState.map { it.text }
            .distinctUntilChanged()
            .onEach {
                updateCurrentTextCountUiState(it.length)
            }.launchIn(viewModelScope)
    }

    private fun observeTextAndUpdateEnableSubmitCTA() {
        uiState.map { it.text }
            .distinctUntilChanged()
            .onEach {
                val enableSubmitCTA = it.isNotBlank()
                updateEnableSubmitCtaUiState(enableSubmitCTA)
            }.launchIn(viewModelScope)
    }

    fun onUiAction(action: FeedbackUiAction) {
        when (action) {
            FeedbackUiAction.OnSubmitButtonClicked -> {
                hitSubmitApi()
            }

            is FeedbackUiAction.OnTextChange -> {
                updateTextUiState(action.text)
            }

            FeedbackUiAction.OnGoBackToHomeButtonClicked -> {
                val screen = when (uiState.value.accountType) {
                    AccountType.USER -> UserNavigation.AccountScreen
                    AccountType.FUEL_STATION -> FuelStationNavigation.AccountScreen
                }
                sendEvent(
                    FeedbackUiEvent.NavigateAndPopup(screen)
                )
            }
        }
    }

    private fun hitSubmitApi() = viewModelScope.launch {
        val feedbackMessage = uiState.value.text
        val request = SubmitFeedbackRequest(feedbackMessage)

        commonRepository.submitFeedback(request)
            .onEach { result ->
                when (result) {
                    is Result.Error -> {
                        Timber.d("SubmitFeedback : Error ${result.error.message}")
                        updateFeedbackScreenType(FeedbackScreenType.ERROR_SCREEN)
                    }

                    is Result.Loading -> {
                        Timber.d("SubmitFeedback : Loading ${result.isLoading}")
                        updateIsLoadingUiState(result.isLoading)
                    }

                    is Result.Success -> {
                        Timber.d("SubmitFeedback : Succes ${result.data}")
                        updateFeedbackScreenType(FeedbackScreenType.SUCCESS_SCREEN)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun updateTextUiState(text: String): Unit =
        _uiState.update {
            it.copy(
                text = text
            )
        }

    private fun updateCurrentTextCountUiState(currentTextCount: Int): Unit =
        _uiState.update {
            it.copy(
                currentTextCount = currentTextCount
            )
        }

    private fun updateEnableSubmitCtaUiState(enableSubmitCTA: Boolean): Unit =
        _uiState.update {
            it.copy(
                enableSubmitCTA = enableSubmitCTA
            )
        }

    private fun updateIsLoadingUiState(isLoading: Boolean): Unit =
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }

    private fun updateFeedbackScreenType(feedbackScreenType: FeedbackScreenType): Unit =
        _uiState.update {
            it.copy(
                feedbackScreenType = feedbackScreenType
            )
        }

    private fun updateAccountType(accountType: AccountType): Unit =
        _uiState.update {
            it.copy(
                accountType = accountType
            )
        }

    private fun sendEvent(event: FeedbackUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}

data class FeedbackUiState(
    val text: String = "",
    val maxTextCount: Int = FeedbackViewModel.MAX_TEXT_COUNT,
    val currentTextCount: Int = 0,
    val enableSubmitCTA: Boolean = false,
    val isLoading: Boolean = false,
    val feedbackScreenType: FeedbackScreenType = FeedbackScreenType.FEEDBACK_COLLECT_SCREEN,
    val accountType: AccountType = AccountType.USER
)

sealed interface FeedbackUiAction {
    data class OnTextChange(val text: String) : FeedbackUiAction
    data object OnSubmitButtonClicked : FeedbackUiAction
    data object OnGoBackToHomeButtonClicked : FeedbackUiAction
}

sealed interface FeedbackUiEvent {
    data class NavigateAndPopup(val screen: NavigationScreen) : FeedbackUiEvent
}

enum class FeedbackScreenType {
    FEEDBACK_COLLECT_SCREEN,
    SUCCESS_SCREEN,
    ERROR_SCREEN
}

