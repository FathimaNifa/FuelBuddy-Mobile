package com.nifa.fuel_buddy.user.presentation.feature.activity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nifa.fuel_buddy.core.domain.util.Result
import com.nifa.fuel_buddy.user.domain.UserRepository
import com.nifa.fuel_buddy.user.domain.model.Product
import com.nifa.fuel_buddy.user.domain.request.GetOrderedProductRequest
import com.nifa.fuel_buddy.user.presentation.navigation.UserNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ActivityDetailScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityDetailScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ActivityDetailScreenUiState()
    )

    init {

        val data = savedStateHandle.toRoute<UserNavigation.ActivityDetailScreen>()

        updateOrderIdUiState(data.orderId)
        updateFuelStationNameUiState(data.fuelStationName)
        updateDeliveryChargeUiState(data.deliveryCharge)

        uiState.map { it.orderId }
            .distinctUntilChanged()
            .flatMapLatest {
                val request = GetOrderedProductRequest(it)
                userRepository.getOrderedProducts(request)
            }.onEach { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        updateProductList(result.data)
                    }
                }
            }.launchIn(viewModelScope)

        uiState.map { it.productList }
            .distinctUntilChanged()
            .filterNotNull()
            .onEach { productList ->
                val deliveryCharge = uiState.value.deliveryCharge
                val totalPrice =
                    (productList.sumOf { it.price * it.quantityAdded }) + deliveryCharge
                updateTotalPrice(totalPrice)
            }.launchIn(viewModelScope)
    }

    private fun updateFuelStationNameUiState(fuelStationName: String): Unit =
        _uiState.update {
            it.copy(
                fuelStationName = fuelStationName
            )
        }

    private fun updateOrderIdUiState(orderId: String): Unit =
        _uiState.update {
            it.copy(
                orderId = orderId
            )
        }

    private fun updateTotalPrice(totalPrice: Long): Unit =
        _uiState.update {
            it.copy(
                totalPrice = totalPrice
            )
        }

    private fun updateProductList(productList: List<Product>): Unit =
        _uiState.update {
            it.copy(
                productList = productList
            )
        }

    private fun updateDeliveryChargeUiState(deliveryCharge: Int): Unit =
        _uiState.update {
            it.copy(
                deliveryCharge = deliveryCharge
            )
        }
}

data class ActivityDetailScreenUiState(
    val fuelStationName: String = "",
    val deliveryCharge: Int = 0,
    val orderId: String = "",
    val productList: List<Product> = emptyList(),
    val totalPrice: Long = 0
)