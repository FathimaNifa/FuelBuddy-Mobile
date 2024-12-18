package com.nifa.fuel_buddy.core.presentation.userFeedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
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
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor() : ViewModel() {

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
                // TODO: To add the api call and navigation
            }

            is FeedbackUiAction.OnTextChange -> {
                updateTextUiState(action.text)
            }
        }
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

    private fun sendEvent(event: FeedbackUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}

data class FeedbackUiState(
    val text: String = "",
    val maxTextCount: Int = FeedbackViewModel.MAX_TEXT_COUNT,
    val currentTextCount: Int = 0,
    val enableSubmitCTA: Boolean = false,
    val isLoading: Boolean = false
)

sealed interface FeedbackUiAction {
    data class OnTextChange(val text: String) : FeedbackUiAction
    data object OnSubmitButtonClicked : FeedbackUiAction
}

sealed interface FeedbackUiEvent {
    data class NavigateAndPopup(val screen: NavigationScreen) : FeedbackUiEvent
}

