package com.nifa.fuel_buddy.user.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class OrderStatusScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderStatusScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = OrderStatusScreenUiState()
    )

    init {
        uiState.map { it.screenState }
            .distinctUntilChanged()
            .filter { it == OrderStatusScreenState.LOADING }
            .onEach {
                delay(3000L)
                updateScreenStateUiState(OrderStatusScreenState.APPROVED)
            }.launchIn(viewModelScope)
    }

    private fun updateScreenStateUiState(screenState: OrderStatusScreenState): Unit =
        _uiState.update {
            it.copy(
                screenState = screenState
            )
        }
}

data class OrderStatusScreenUiState(
    val screenState: OrderStatusScreenState = OrderStatusScreenState.LOADING
)

enum class OrderStatusScreenState {
    LOADING,
    APPROVED
}