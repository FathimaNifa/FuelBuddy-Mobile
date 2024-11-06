package com.nifa.fuel_buddy.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.domain.FuelStation
import com.nifa.fuel_buddy.domain.Product
import com.nifa.fuel_buddy.presentation.core.navigation.NavigationScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeDetailScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeDetailScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeDetailScreenUiState()
    )

    private val _uiEvent = Channel<HomeDetailScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        uiState.map { it.fuelStation }
            .distinctUntilChanged()
            .filterNotNull()
            .onEach {
                updateTitleUiState(it.name)
                updateProductListUiState(it.productList)
            }
            .launchIn(viewModelScope)
    }


    fun onUiAction(action: HomeDetailUiAction) {
        when (action) {
            is HomeDetailUiAction.AddButtonClicked -> {

                updateProductQuantity(
                    productId = action.productId,
                    action = {
                        it + 1
                    }
                )
            }

            is HomeDetailUiAction.ReduceButtonClicked -> {

                updateProductQuantity(
                    productId = action.productId,
                    action = {
                        it - 1
                    }
                )
            }
        }
    }

    private fun updateProductQuantity(productId: Long, action: (Int) -> Int) {

        val newList = _uiState.value.productList.map {
            if (it.productId == productId) {
                val updatedQuantity = action.invoke(it.quantityAdded)
                it.copy(
                    quantityAdded = updatedQuantity
                )
            } else
                it
        }

        updateProductListUiState(newList)
    }

    private fun sendEvent(event: HomeDetailScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    fun updateFuelStationUiState(fuelStation: FuelStation?): Unit =
        _uiState.update {
            it.copy(
                fuelStation = fuelStation
            )
        }

    private fun updateProductListUiState(productList: List<Product>) {
        _uiState.update {
            it.copy(
                productList = productList
            )
        }
    }

    private fun updateTitleUiState(title: String): Unit =
        _uiState.update {
            it.copy(
                title = title
            )
        }

}

data class HomeDetailScreenUiState(
    val fuelStation: FuelStation? = null,
    val title: String = "",
    val productList: List<Product> = emptyList()
)

sealed interface HomeDetailUiAction {

    data class AddButtonClicked(val productId: Long) : HomeDetailUiAction

    data class ReduceButtonClicked(val productId: Long) : HomeDetailUiAction
}

sealed interface HomeDetailScreenUiEvent {
    data class NavigateTo(val navigationScreen: NavigationScreen) : HomeDetailScreenUiEvent
}