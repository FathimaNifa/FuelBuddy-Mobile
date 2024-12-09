package com.nifa.fuel_buddy.fuelstation.data.repository

import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.fuelstation.data.model.OrderDetailsDto
import com.nifa.fuel_buddy.fuelstation.data.model.ProductDetailsDto
import com.nifa.fuel_buddy.fuelstation.data.model.toOrderDetails
import com.nifa.fuel_buddy.fuelstation.data.model.toProduct
import com.nifa.fuel_buddy.fuelstation.data.networkSource.FuelStationNetworkSource
import com.nifa.fuel_buddy.fuelstation.domain.FuelStationRepository
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetCustomerOrdersRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderHistoryRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderedHistoryProductsRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderedProductsRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.UpdateDriverLocationRequest
import com.nifa.fuel_buddy.user.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FuelStationRepositoryImpl @Inject constructor(
    private val networkSource: FuelStationNetworkSource
) : FuelStationRepository {
    override suspend fun getCustomerOrders(request: GetCustomerOrdersRequest): Flow<Result<List<OrderDetails>, NetworkError>> {
        return networkSource.getCustomerOrders(request).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.data.map(OrderDetailsDto::toOrderDetails))
            }
        }
    }

    override suspend fun getOrderedProducts(request: GetOrderedProductsRequest): Flow<Result<List<Product>, NetworkError>> {
        return networkSource.getOrderedProducts(request).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.data.map(ProductDetailsDto::toProduct))
            }
        }
    }

    override suspend fun getOrderHistory(request: GetOrderHistoryRequest): Flow<Result<List<OrderDetails>, NetworkError>> {
        return networkSource.getOrderHistory(request).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.data.map(OrderDetailsDto::toOrderDetails))
            }
        }
    }

    override suspend fun getOrderHistoryProducts(request: GetOrderedHistoryProductsRequest): Flow<Result<List<Product>, NetworkError>> {
        return networkSource.getOrderHistoryProducts(request).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.data.map(ProductDetailsDto::toProduct))
            }
        }
    }

    override suspend fun updateDriverLocation(request: UpdateDriverLocationRequest): Flow<Result<Unit, NetworkError>> {
        return networkSource.updateDriverLocation(request).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(Unit)
            }
        }
    }
}