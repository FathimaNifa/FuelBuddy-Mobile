package com.nifa.fuel_buddy.fuelstation.presentation.feature.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nifa.fuel_buddy.core.domain.util.Result
import com.nifa.fuel_buddy.core.utils.CustomNavType
import com.nifa.fuel_buddy.fuelstation.domain.FuelStationRepository
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderStatus
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderedProductsRequest
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.FuelStationNavigation
import com.nifa.fuel_buddy.user.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HistoryDetailViewModel @Inject constructor(
    private val repository: FuelStationRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryDetailScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HistoryDetailScreenUiState()
    )

    init {
        val data = savedStateHandle.toRoute<FuelStationNavigation.HistoryDetailScreen>(
            typeMap = mapOf(
                typeOf<OrderDetails>() to CustomNavType.OrderDetailsType,
            )
        )

        updateOrderIdUiState(data.orderDetails.orderId)
        updateOrderNumberUiState(data.orderDetails.orderNumber)
        updateLocationUiState(data.orderDetails.location)
        updateDeliveryChargeUiState(data.orderDetails.deliveryCharge)
        updateTotalPriceUiState(data.orderDetails.totalPrice)
        updateUserNameUiState(data.orderDetails.userName)
        updateOrderStatusUiState(data.orderDetails.orderStatus)

        uiState.map { it.orderId }
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .flatMapLatest {
                val request = GetOrderedProductsRequest(it)
                repository.getOrderHistoryProducts(request)
            }
            .onEach { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> { updateProductListUiState(result.data) }
                }
            }.launchIn(viewModelScope)
    }

    private fun updateOrderNumberUiState(orderNumber: String) =
        _uiState.update {
            it.copy(
                orderNumber = orderNumber
            )
        }

    private fun updateOrderIdUiState(orderId: String) =
        _uiState.update {
            it.copy(
                orderId = orderId
            )
        }

    private fun updateLocationUiState(location: String) =
        _uiState.update {
            it.copy(
                location = location
            )
        }

    private fun updateTotalPriceUiState(totalPrice: String) =
        _uiState.update {
            it.copy(
                totalPrice = totalPrice
            )
        }

    private fun updateDeliveryChargeUiState(deliveryCharge: String) =
        _uiState.update {
            it.copy(
                deliveryCharge = deliveryCharge
            )
        }

    private fun updateUserNameUiState(userName: String) =
        _uiState.update {
            it.copy(
                userName = userName
            )
        }

    private fun updateOrderStatusUiState(orderStatus: OrderStatus) =
        _uiState.update {
            it.copy(
                orderStatus = orderStatus
            )
        }

    private fun updateProductListUiState(productList: List<Product>) =
        _uiState.update {
            it.copy(
                productList = productList
            )
        }
}

data class HistoryDetailScreenUiState(
    val orderNumber: String = "",
    val orderId: String = "",
    val location: String = "",
    val totalPrice: String = "",
    val deliveryCharge: String = "",
    val userName: String = "",
    val orderStatus: OrderStatus = OrderStatus.DELIVERED,
    val productList: List<Product> = emptyList()
)
