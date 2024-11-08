package com.nifa.fuel_buddy.presentation.feature.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.domain.FuelStation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ActivityDetailScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityDetailScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ActivityDetailScreenUiState()
    )

    init {
        uiState.map { it.fuelStation }
            .distinctUntilChanged()
            .filterNotNull()
            .onEach {fuelStation ->
                val totalPrice = (fuelStation.productList.sumOf { it.price * it.quantityAdded }) + fuelStation.deliveryCharge
                updateTotalPrice(totalPrice)
            }.launchIn(viewModelScope)
    }

    fun updateFuelStationUiState(fuelStation: FuelStation): Unit =
        _uiState.update {
            it.copy(
                fuelStation = fuelStation
            )
        }

    private fun updateTotalPrice(totalPrice: Long): Unit =
        _uiState.update {
            it.copy(
                totalPrice = totalPrice
            )
        }
}

data class ActivityDetailScreenUiState(
    val fuelStation: FuelStation? = null,
    val totalPrice: Long = 0
)