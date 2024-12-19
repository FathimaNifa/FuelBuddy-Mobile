package com.nifa.fuel_buddy.fuelstation.data.networkSource

import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.domain.SuccessResponse
import com.nifa.fuel_buddy.fuelstation.data.model.GetCustomerOrderDto
import com.nifa.fuel_buddy.fuelstation.data.model.GetOrderedProductDetailsDto
import com.nifa.fuel_buddy.fuelstation.data.model.OrderDetailsDto
import com.nifa.fuel_buddy.fuelstation.data.model.ProductDetailsDto
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderStatus
import com.nifa.fuel_buddy.fuelstation.domain.model.request.AcceptOrderRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetCustomerOrdersRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderHistoryRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderedProductsRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.UpdateDriverLocationRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class FakeFuelStationNetworkSource @Inject constructor() : FuelStationNetworkSource {
    override suspend fun getCustomerOrders(request: GetCustomerOrdersRequest): Flow<Result<GetCustomerOrderDto, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            delay(500L)
            emit(
                Result.Success(
                    GetCustomerOrderDto(
                        statusCode = 200,
                        message = "Success",
                        data = listOf(
                            OrderDetailsDto(
                                orderNumber = "22",
                                orderId = "1",
                                location = "Sakthi Vinayakar Nagar, Injambakkam Chennai, Tamil Nadu 600115",
                                awayFrom = "5.5km away",
                                userName = "Kannan G",
                                totalPrice = "650",
                                deliveryCharge = "20",
                                orderStatus = OrderStatus.New.name,
                                dateAndTime = "",
                                latitude = 13.0437639,
                                longitude = 80.2632539
                            )
                        )
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun getLiveCustomerOrders(): Flow<OrderDetailsDto> {
        return flow {
            emit(
                OrderDetailsDto(
                    orderNumber = "22",
                    orderId = "1",
                    location = "Sakthi Vinayakar Nagar, Injambakkam Chennai, Tamil Nadu 600115",
                    awayFrom = "5.5km away",
                    userName = "Kannan G",
                    totalPrice = "650",
                    deliveryCharge = "20",
                    orderStatus = OrderStatus.New.name,
                    dateAndTime = "",
                    latitude = 13.0437639,
                    longitude = 80.2632539
                )
            )
        }
    }

    override suspend fun getOrderedProducts(request: GetOrderedProductsRequest): Flow<Result<GetOrderedProductDetailsDto, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            delay(500L)
            emit(
                Result.Success(
                    GetOrderedProductDetailsDto(
                        statusCode = 200,
                        message = "Success",
                        data = listOf(
                            ProductDetailsDto(
                                productId = "1",
                                productName = "Petrol",
                                productImage = "",
                                productPrice = 500,
                                quantity = 2
                            )
                        )
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun getOrderHistory(request: GetOrderHistoryRequest): Flow<Result<GetCustomerOrderDto, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            delay(500L)
            emit(
                Result.Success(
                    GetCustomerOrderDto(
                        statusCode = 200,
                        message = "Success",
                        data = listOf(
                            OrderDetailsDto(
                                orderNumber = "22",
                                orderId = "1",
                                location = "Sakthi Vinayakar Nagar, Injambakkam Chennai, Tamil Nadu 600115",
                                awayFrom = "5.5km away",
                                userName = "Kannan G",
                                totalPrice = "650",
                                deliveryCharge = "20",
                                orderStatus = OrderStatus.DELIVERED.name,
                                dateAndTime = "20 Oct | 1.41 pm",
                                latitude = 13.0437639,
                                longitude = 80.2632539
                            )
                        )
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun getOrderHistoryProducts(request: GetOrderedProductsRequest): Flow<Result<GetOrderedProductDetailsDto, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            delay(500L)
            emit(
                Result.Success(
                    GetOrderedProductDetailsDto(
                        statusCode = 200,
                        message = "Success",
                        data = listOf(
                            ProductDetailsDto(
                                productId = "1",
                                productName = "Petrol",
                                productImage = "",
                                productPrice = 500,
                                quantity = 2
                            )
                        )
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun updateDriverLocation(request: UpdateDriverLocationRequest): Flow<Result<SuccessResponse, NetworkError>> {
        return flow {
            Timber.d("updateDriverLocation : $request")
            emit(
                Result.Success(
                    SuccessResponse(
                        statusCode = 200,
                        message = "Success"
                    )
                )
            )
        }
    }

    override suspend fun acceptOrder(request: AcceptOrderRequest): Flow<Result<SuccessResponse, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            delay(500L)
            emit(
                Result.Success(
                    SuccessResponse(
                        statusCode = 200,
                        message = "Success"
                    )
                )
            )
        }
    }
}