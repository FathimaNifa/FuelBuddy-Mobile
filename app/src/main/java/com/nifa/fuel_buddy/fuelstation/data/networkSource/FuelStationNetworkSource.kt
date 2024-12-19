package com.nifa.fuel_buddy.fuelstation.data.networkSource

import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.domain.SuccessResponse
import com.nifa.fuel_buddy.fuelstation.data.model.GetCustomerOrderDto
import com.nifa.fuel_buddy.fuelstation.data.model.GetOrderedProductDetailsDto
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetCustomerOrdersRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderHistoryRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderedProductsRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.UpdateDriverLocationRequest
import kotlinx.coroutines.flow.Flow

interface FuelStationNetworkSource {

    suspend fun getCustomerOrders(request: GetCustomerOrdersRequest): Flow<Result<GetCustomerOrderDto, NetworkError>>

    suspend fun getOrderedProducts(request: GetOrderedProductsRequest): Flow<Result<GetOrderedProductDetailsDto, NetworkError>>

    suspend fun getOrderHistory(request: GetOrderHistoryRequest): Flow<Result<GetCustomerOrderDto, NetworkError>>

    suspend fun getOrderHistoryProducts(request: GetOrderedProductsRequest): Flow<Result<GetOrderedProductDetailsDto, NetworkError>>

    suspend fun updateDriverLocation(request: UpdateDriverLocationRequest): Flow<Result<SuccessResponse, NetworkError>>
}
