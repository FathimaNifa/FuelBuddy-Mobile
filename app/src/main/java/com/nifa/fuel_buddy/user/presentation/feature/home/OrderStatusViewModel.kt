package com.nifa.fuel_buddy.user.presentation.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDecision
import com.nifa.fuel_buddy.user.domain.UserRepository
import com.nifa.fuel_buddy.user.presentation.navigation.UserNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@HiltViewModel
class OrderStatusScreenViewModel @Inject constructor(
    userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderStatusScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = OrderStatusScreenUiState()
    )

    private val _uiEvent = Channel<OrderStatusUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val orderId = savedStateHandle.toRoute<UserNavigation.OrderStatusScreen>().orderId

    init {
        uiState.map { it.screenState }
            .distinctUntilChanged()
            .filter { it == OrderStatusScreenState.LOADING }
            .onEach {
                delay(3000L)
                updateScreenStateUiState(OrderStatusScreenState.APPROVED)
            }.launchIn(viewModelScope)

        viewModelScope.launch {
            userRepository.orderResponse()
                .onEach { orderDecision ->
                    when (orderDecision) {
                        OrderDecision.ORDERED -> updateScreenStateUiState(OrderStatusScreenState.LOADING)
                        OrderDecision.ACCEPTED -> updateScreenStateUiState(OrderStatusScreenState.APPROVED)
                        OrderDecision.DECLINED -> updateScreenStateUiState(OrderStatusScreenState.DECLINED)
                    }
                }.launchIn(this)
        }

    }

    fun onUiAction(action: OrderStatusScreenUiAction) {
        when (action) {
            OrderStatusScreenUiAction.OnGoBackToHomeButtonClicked -> {
                sendEvent(
                    OrderStatusUiEvent.NavigateAndPopupToCallback(UserNavigation.HomeScreen)
                )
            }

            OrderStatusScreenUiAction.OnTrackOrderButtonClicked -> {
                sendEvent(
                    OrderStatusUiEvent.NavigateToCallback(
                        UserNavigation.OrderTrackingScreen(
                            orderId = orderId
                        )
                    )
                )
            }
        }
    }

    private fun updateScreenStateUiState(screenState: OrderStatusScreenState): Unit =
        _uiState.update {
            it.copy(
                screenState = screenState
            )
        }

    private fun sendEvent(event: OrderStatusUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}

data class OrderStatusScreenUiState(
    val screenState: OrderStatusScreenState = OrderStatusScreenState.LOADING
)

sealed interface OrderStatusScreenUiAction {
    data object OnTrackOrderButtonClicked : OrderStatusScreenUiAction
    data object OnGoBackToHomeButtonClicked : OrderStatusScreenUiAction
}

sealed interface OrderStatusUiEvent {
    data class NavigateAndPopupToCallback(val screen: UserNavigation) : OrderStatusUiEvent
    data class NavigateToCallback(val screen: UserNavigation) : OrderStatusUiEvent
}

enum class OrderStatusScreenState {
    LOADING,
    APPROVED,
    DECLINED
}