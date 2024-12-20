package com.nifa.fuel_buddy.user.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.domain.location.LocationClient
import com.nifa.fuel_buddy.core.domain.model.LatLong
import com.nifa.fuel_buddy.core.domain.util.Result
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.user.domain.UserRepository
import com.nifa.fuel_buddy.user.domain.model.FuelStation
import com.nifa.fuel_buddy.user.domain.model.Product
import com.nifa.fuel_buddy.user.domain.model.toCartItem
import com.nifa.fuel_buddy.user.domain.request.GetAllProductRequest
import com.nifa.fuel_buddy.user.domain.request.OrderProductsRequest
import com.nifa.fuel_buddy.user.presentation.navigation.UserNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HomeDetailScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    locationClient: LocationClient
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeDetailScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeDetailScreenUiState()
    )

    private val _uiEvent = Channel<HomeDetailScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {

        getProductList()

        uiState.map { it.fuelStation }
            .distinctUntilChanged()
            .filterNotNull()
            .onEach {
                updateImageUrlUiState(it.imageUrl)
                updateTitleUiState(it.name)
//                updateProductListUiState(it.productList)
            }.launchIn(viewModelScope)


        uiState.map { it.productList }
            .distinctUntilChanged()
            .onEach { products: List<Product> ->
                val addedItemCount = products.count { it.quantityAdded > 0 }
                val productListAddedInTheCart = products.filter { it.quantityAdded > 0 }
                updateAddedItemCountUiState(addedItemCount = addedItemCount)
                updateProductListAddedInTheCart(productListAddedInTheCart)
            }.launchIn(viewModelScope)

        uiState.map { it.addedItemCount }
            .distinctUntilChanged()
            .onEach {
                val shouldShowCartCTABottomSheet = it > 0
                updateShouldShowCartCTABottomSheet(shouldShowCartCTABottomSheet)
            }.launchIn(viewModelScope)

        uiState.map { it.productListAddedInTheCart }
            .distinctUntilChanged()
            .onEach { products: List<Product> ->
                val deliveryCharges = uiState.value.fuelStation?.deliveryCharge ?: 0
                val totalPrice = (products.sumOf { it.price * it.quantityAdded }) + deliveryCharges
                updateTotalPriceUiState(totalPrice)
                if (products.isEmpty()) {
                    val screenState = uiState.value.homeDetailScreenState
                    if (screenState == HomeDetailScreenState.CART)
                        updateHomeDetailScreenStateUiState(HomeDetailScreenState.DETAIL)
                }
            }.launchIn(viewModelScope)

        locationClient.getCurrentLocation()
            .onEach {
                val latLong = LatLong(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
                updateCurrentLocation(latLong)
            }.launchIn(viewModelScope)
    }

    private fun getProductList() {
        uiState.map { it.fuelStation }
            .filterNotNull()
            .distinctUntilChanged()
            .flatMapLatest { fuelStation ->
                val request = GetAllProductRequest(fuelStation.id)
                userRepository.getAllProducts(request)
            }.onEach { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        updateProductListUiState(result.data)
                    }
                }
            }.launchIn(viewModelScope)
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

            HomeDetailUiAction.ViewCartButtonClicked -> {
                updateHomeDetailScreenStateUiState(HomeDetailScreenState.CART)
            }

            HomeDetailUiAction.OnBackPressClicked -> {
                updateHomeDetailScreenStateUiState(HomeDetailScreenState.DETAIL)
            }

            HomeDetailUiAction.OrderNowButtonClicked -> orderProducts()
        }
    }

    private fun orderProducts() = viewModelScope.launch {
        val bunkId = uiState.value.fuelStation?.id ?: ""
        val currentLocation = uiState.value.currentLocation
        val cartItems = uiState.value.productListAddedInTheCart.map(Product::toCartItem)

        val request = OrderProductsRequest(
            bunkId = bunkId,
            deliveryLocation = currentLocation,
            cartItems = cartItems
        )

        userRepository.orderProducts(request)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        val orderId = result.data.orderId
                        val screen = UserNavigation.OrderStatusScreen(orderId)
                        sendEvent(
                            HomeDetailScreenUiEvent.NavigateTo(screen)
                        )
                    }
                }
            }.launchIn(viewModelScope)

    }

    private fun updateProductQuantity(productId: String, action: (Int) -> Int) {

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

    private fun updateAddedItemCountUiState(addedItemCount: Int): Unit =
        _uiState.update {
            it.copy(
                addedItemCount = addedItemCount
            )
        }

    private fun updateShouldShowCartCTABottomSheet(shouldShowCartCTABottomSheet: Boolean): Unit =
        _uiState.update {
            it.copy(
                shouldShowCartCTABottomSheet = shouldShowCartCTABottomSheet
            )
        }

    private fun updateProductListAddedInTheCart(productListAddedInTheCart: List<Product>): Unit =
        _uiState.update {
            it.copy(
                productListAddedInTheCart = productListAddedInTheCart
            )
        }

    private fun updateTotalPriceUiState(totalPrice: Long): Unit =
        _uiState.update {
            it.copy(
                totalPrice = totalPrice
            )
        }

    private fun updateHomeDetailScreenStateUiState(homeDetailScreenState: HomeDetailScreenState): Unit =
        _uiState.update {
            it.copy(
                homeDetailScreenState = homeDetailScreenState
            )
        }

    private fun updateImageUrlUiState(imageUrl: String): Unit =
        _uiState.update {
            it.copy(
                imageUrl = imageUrl
            )
        }

    private fun updateCurrentLocation(latLong: LatLong): Unit =
        _uiState.update {
            it.copy(
                currentLocation = latLong
            )
        }

}

data class HomeDetailScreenUiState(
    val fuelStation: FuelStation? = null,
    val title: String = "",
    val imageUrl: String = "",
    val productList: List<Product> = emptyList(),
    val addedItemCount: Int = 0,
    val currentLocation: LatLong = LatLong(0.0, 0.0),
    val shouldShowCartCTABottomSheet: Boolean = false,
    val productListAddedInTheCart: List<Product> = emptyList(),
    val totalPrice: Long = 0,
    val homeDetailScreenState: HomeDetailScreenState = HomeDetailScreenState.DETAIL
)

sealed interface HomeDetailUiAction {

    data class AddButtonClicked(val productId: String) : HomeDetailUiAction

    data class ReduceButtonClicked(val productId: String) : HomeDetailUiAction

    data object ViewCartButtonClicked : HomeDetailUiAction

    data object OnBackPressClicked : HomeDetailUiAction

    data object OrderNowButtonClicked : HomeDetailUiAction
}

sealed interface HomeDetailScreenUiEvent {
    data class NavigateTo(val navigationScreen: NavigationScreen) : HomeDetailScreenUiEvent
}

enum class HomeDetailScreenState {
    DETAIL,
    CART
}