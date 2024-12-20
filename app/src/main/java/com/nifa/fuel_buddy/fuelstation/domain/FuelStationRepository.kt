package com.nifa.fuel_buddy.fuelstation.domain

import com.nifa.fuel_buddy.core.domain.util.NetworkError
import com.nifa.fuel_buddy.core.domain.util.Result
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
import com.nifa.fuel_buddy.fuelstation.domain.model.request.AcceptOrderRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetCustomerOrdersRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderHistoryRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderedProductsRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.OrderDeliveredRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.UpdateDriverLocationRequest
import com.nifa.fuel_buddy.user.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface FuelStationRepository {

    suspend fun getLiveCustomerOrder(): Flow<OrderDetails>

    suspend fun getCustomerOrders(request: GetCustomerOrdersRequest): Flow<Result<List<OrderDetails>, NetworkError>>

    suspend fun getOrderedProducts(request: GetOrderedProductsRequest): Flow<Result<List<Product>, NetworkError>>

    suspend fun getOrderHistory(request: GetOrderHistoryRequest): Flow<Result<List<OrderDetails>, NetworkError>>

    suspend fun getOrderHistoryProducts(request: GetOrderedProductsRequest): Flow<Result<List<Product>, NetworkError>>

    suspend fun updateDriverLocation(request: UpdateDriverLocationRequest): Flow<Result<Unit, NetworkError>>

    suspend fun acceptOrder(request: AcceptOrderRequest): Flow<Result<Unit, NetworkError>>

    suspend fun orderDelivered(request : OrderDeliveredRequest) : Flow<Result<Unit, NetworkError>>
}