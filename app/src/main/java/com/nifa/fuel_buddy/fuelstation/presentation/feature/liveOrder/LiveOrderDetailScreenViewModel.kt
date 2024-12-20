package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.utils.CustomNavType
import com.nifa.fuel_buddy.fuelstation.domain.FuelStationRepository
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDecision
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
import com.nifa.fuel_buddy.fuelstation.domain.model.request.AcceptOrderRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderedProductsRequest
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.FuelStationNavigation
import com.nifa.fuel_buddy.fuelstation.presentation.service.LocationUpdateService
import com.nifa.fuel_buddy.user.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class LiveOrderDetailScreenViewModel @Inject constructor(
    private val repository: FuelStationRepository,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val applicationContext: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(LiveOrderDetailScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = LiveOrderDetailScreenUiState()
    )

    private val _uiEvent = Channel<LiveOrderDetailScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val data = savedStateHandle.toRoute<FuelStationNavigation.LiveOrderDetailScreen>(
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
        updateLatitudeUiState(data.orderDetails.latitude)
        updateLongitudeUiState(data.orderDetails.longitude)

        uiState.map { it.orderId }
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .flatMapLatest {
                val request = GetOrderedProductsRequest(it)
                repository.getOrderedProducts(request)
            }
            .onEach { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        updateProductListUiState(result.data)
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun onUiAction(action: LiveOrderDetailUiAction) {
        when (action) {
            LiveOrderDetailUiAction.OnAcceptButtonClicked -> {

                hitAcceptOrderApi(
                    type = OrderDecision.ACCEPTED,
                    orderId = uiState.value.orderId
                )
                startLocationUpdateService(
                    latitude = uiState.value.latitude,
                    longitude = uiState.value.longitude,
                    orderId = uiState.value.orderId
                )

                val screen = FuelStationNavigation.OutForDeliveryScreen(
                    latitude = uiState.value.latitude,
                    longitude = uiState.value.longitude,
                    orderId = uiState.value.orderId
                )
                sendEvent(LiveOrderDetailScreenUiEvent.NavigateTo(screen))
            }

            LiveOrderDetailUiAction.OnDeclineButtonClicked -> {
                hitAcceptOrderApi(
                    type = OrderDecision.DECLINED,
                    orderId = uiState.value.orderId
                )
            }
        }
    }

    private fun hitAcceptOrderApi(type: OrderDecision, orderId: String) = viewModelScope.launch {

        val request = AcceptOrderRequest(
            orderId = orderId,
            type = type.name
        )

        repository.acceptOrder(request)
    }

    private fun startLocationUpdateService(
        latitude: Double,
        longitude: Double,
        orderId: String
    ) {

        val bundle = Bundle().apply {
            putDouble(LocationUpdateService.LATITUDE, latitude)
            putDouble(LocationUpdateService.LONGITUDE, longitude)
            putString(LocationUpdateService.ORDER_ID, orderId)
        }
        val intent = Intent(applicationContext, LocationUpdateService::class.java).apply {
            action = LocationUpdateService.ACTION_START
            putExtras(bundle)
        }
        applicationContext.startService(intent)
    }

    private fun sendEvent(event: LiveOrderDetailScreenUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
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

    private fun updateProductListUiState(productList: List<Product>) =
        _uiState.update {
            it.copy(
                productList = productList
            )
        }

    private fun updateLatitudeUiState(latitude: Double) =
        _uiState.update {
            it.copy(
                latitude = latitude
            )
        }

    private fun updateLongitudeUiState(longitude: Double) =
        _uiState.update {
            it.copy(
                longitude = longitude
            )
        }
}

data class LiveOrderDetailScreenUiState(
    val orderNumber: String = "",
    val orderId: String = "",
    val location: String = "",
    val totalPrice: String = "",
    val deliveryCharge: String = "",
    val userName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val productList: List<Product> = emptyList()
)

sealed interface LiveOrderDetailUiAction {
    data object OnAcceptButtonClicked : LiveOrderDetailUiAction
    data object OnDeclineButtonClicked : LiveOrderDetailUiAction
}

sealed interface LiveOrderDetailScreenUiEvent {
    data class NavigateTo(val screen: FuelStationNavigation) : LiveOrderDetailScreenUiEvent
}