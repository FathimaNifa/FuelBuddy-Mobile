package com.nifa.fuel_buddy.user.domain

import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.domain.model.LatLong
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDecision
import com.nifa.fuel_buddy.user.domain.model.FuelOrderHistory
import com.nifa.fuel_buddy.user.domain.model.FuelStation
import com.nifa.fuel_buddy.user.domain.model.Product
import com.nifa.fuel_buddy.user.domain.request.GetAllProductRequest
import com.nifa.fuel_buddy.user.domain.request.GetNearbyFuelStationRequest
import com.nifa.fuel_buddy.user.domain.request.GetOrderedProductRequest
import com.nifa.fuel_buddy.user.domain.request.TrackOrderRequest
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getNearbyFuelStation(request: GetNearbyFuelStationRequest): Flow<Result<List<FuelStation>, NetworkError>>

    suspend fun getFuelOrderHistory(): Flow<Result<List<FuelOrderHistory>, NetworkError>>

    suspend fun getAllProducts(request: GetAllProductRequest): Flow<Result<List<Product>, NetworkError>>

    suspend fun getOrderedProducts(request: GetOrderedProductRequest): Flow<Result<List<Product>, NetworkError>>

    suspend fun trackOrder(request: TrackOrderRequest): Flow<Result<LatLong, NetworkError>>

    suspend fun orderResponse(): Flow<OrderDecision>
}