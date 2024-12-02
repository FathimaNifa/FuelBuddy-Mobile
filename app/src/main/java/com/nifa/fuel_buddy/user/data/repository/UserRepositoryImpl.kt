package com.nifa.fuel_buddy.user.data.repository

import com.google.android.gms.maps.model.LatLng
import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.user.data.model.FuelOrderHistoryDto
import com.nifa.fuel_buddy.user.data.model.FuelStationDto
import com.nifa.fuel_buddy.user.data.model.OrderedProductsDto
import com.nifa.fuel_buddy.user.data.model.ProductDto
import com.nifa.fuel_buddy.user.data.model.toFuelOrderHistory
import com.nifa.fuel_buddy.user.data.model.toFuelStation
import com.nifa.fuel_buddy.user.data.model.toProduct
import com.nifa.fuel_buddy.user.data.networkSource.UserNetworkSource
import com.nifa.fuel_buddy.user.domain.UserRepository
import com.nifa.fuel_buddy.user.domain.model.FuelOrderHistory
import com.nifa.fuel_buddy.user.domain.model.FuelStation
import com.nifa.fuel_buddy.user.domain.model.Product
import com.nifa.fuel_buddy.user.domain.request.GetAllProductRequest
import com.nifa.fuel_buddy.user.domain.request.GetNearbyFuelStationRequest
import com.nifa.fuel_buddy.user.domain.request.GetOrderedProductRequest
import com.nifa.fuel_buddy.user.domain.request.TrackOrderRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userNetworkSource: UserNetworkSource
) : UserRepository {
    override suspend fun getNearbyFuelStation(request: GetNearbyFuelStationRequest): Flow<Result<List<FuelStation>, NetworkError>> {
        return userNetworkSource.getNearbyFuelStation(request).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.data.map(FuelStationDto::toFuelStation))
            }
        }
    }

    override suspend fun getFuelOrderHistory(): Flow<Result<List<FuelOrderHistory>, NetworkError>> {
        return userNetworkSource.getFuelOrderHistory().map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.data.map(FuelOrderHistoryDto::toFuelOrderHistory))
            }
        }
    }

    override suspend fun getAllProducts(request: GetAllProductRequest): Flow<Result<List<Product>, NetworkError>> {
        return userNetworkSource.getAllProducts(request).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.data.map(ProductDto::toProduct))
            }
        }
    }

    override suspend fun getOrderedProducts(request: GetOrderedProductRequest): Flow<Result<List<Product>, NetworkError>> {
        return userNetworkSource.getOrderedProducts(request).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(result.data.data.map(OrderedProductsDto::toProduct))
            }
        }
    }

    override suspend fun trackOrder(request: TrackOrderRequest): Flow<Result<LatLng, NetworkError>> {
        return userNetworkSource.trackOrder(request)
    }
}