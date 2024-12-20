package com.nifa.fuel_buddy.fuelstation.data.networkSource

import android.content.Context
import com.nifa.fuel_buddy.core.domain.BaseApiResponse
import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.domain.SuccessResponse
import com.nifa.fuel_buddy.fuelstation.data.model.GetCustomerOrderDto
import com.nifa.fuel_buddy.fuelstation.data.model.GetOrderedProductDetailsDto
import com.nifa.fuel_buddy.fuelstation.data.model.OrderDetailsDto
import com.nifa.fuel_buddy.fuelstation.domain.model.request.AcceptOrderRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetCustomerOrdersRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderHistoryRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderedProductsRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.OrderDeliveredRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.UpdateDriverLocationRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FuelStationNetworkSourceImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val api: FuelStationApi,
    private val socketSource: FuelStationSocketSource
) : FuelStationNetworkSource, BaseApiResponse(
    context
) {
    override suspend fun getCustomerOrders(request: GetCustomerOrdersRequest): Flow<Result<GetCustomerOrderDto, NetworkError>> {
        return safeApiCall { api.getCustomerOrders(request) }
    }

    override suspend fun getLiveCustomerOrders(): Flow<OrderDetailsDto> {
        return socketSource.getCustomerOrders()
    }

    override suspend fun getOrderedProducts(request: GetOrderedProductsRequest): Flow<Result<GetOrderedProductDetailsDto, NetworkError>> {
        return safeApiCall { api.getOrderedProducts(request) }
    }

    override suspend fun getOrderHistory(request: GetOrderHistoryRequest): Flow<Result<GetCustomerOrderDto, NetworkError>> {
        return safeApiCall { api.getOrderHistory(request) }
    }

    override suspend fun getOrderHistoryProducts(request: GetOrderedProductsRequest): Flow<Result<GetOrderedProductDetailsDto, NetworkError>> {
        return safeApiCall { api.getOrderedProducts(request) }
    }

    override suspend fun updateDriverLocation(request: UpdateDriverLocationRequest): Flow<Result<SuccessResponse, NetworkError>> {
        return safeApiCall { api.updateLocation(request) }
    }

    override suspend fun acceptOrder(request: AcceptOrderRequest): Flow<Result<SuccessResponse, NetworkError>> {
        return safeApiCall { api.acceptOrder(request) }
    }

    override suspend fun orderDelivered(request: OrderDeliveredRequest): Flow<Result<SuccessResponse, NetworkError>> {
        return safeApiCall { api.orderDelivered(request) }
    }
}