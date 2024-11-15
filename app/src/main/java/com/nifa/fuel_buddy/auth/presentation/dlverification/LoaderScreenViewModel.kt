package com.nifa.fuel_buddy.auth.presentation.dlverification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.navigation.NavigationScreen
import com.nifa.fuel_buddy.user.presentation.navigation.UserNavigation
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoaderScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoaderScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = LoaderScreenUiState()
    )


    private val _uiEvent = Channel<LoaderScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        uiState.map { it.screenState }
            .distinctUntilChanged()
            .filter { it == LoaderScreenState.LOADING }
            .onEach {
                delay(2000L)
                updateScreenStateUiState(LoaderScreenState.VERIFIED)
            }.launchIn(viewModelScope)

        uiState.map { it.screenState }
            .distinctUntilChanged()
            .filter { it == LoaderScreenState.VERIFIED }
            .onEach {
                delay(1500L)
                sendEvent(LoaderScreenUiEvent.NavigateAndClearBackStack(UserNavigation.UserNavGraph))
            }.launchIn(viewModelScope)
    }


    private fun updateScreenStateUiState(screenState: LoaderScreenState): Unit =
        _uiState.update {
            it.copy(
                screenState = screenState
            )
        }

    private fun sendEvent(event: LoaderScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

}

sealed interface LoaderScreenUiEvent {
    data class NavigateAndClearBackStack(val navigationScreen: NavigationScreen) :
        LoaderScreenUiEvent
}

data class LoaderScreenUiState(
    val screenState: LoaderScreenState = LoaderScreenState.LOADING
)

enum class LoaderScreenState {
    LOADING,
    VERIFIED
}