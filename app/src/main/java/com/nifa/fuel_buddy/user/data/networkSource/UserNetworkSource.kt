package com.nifa.fuel_buddy.user.data.networkSource

import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.domain.SuccessResponse
import com.nifa.fuel_buddy.core.domain.model.LatLong
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDecision
import com.nifa.fuel_buddy.user.data.model.GetAllProductsDto
import com.nifa.fuel_buddy.user.data.model.GetFuelOrderHistoryDto
import com.nifa.fuel_buddy.user.data.model.GetNearbyFuelStationDto
import com.nifa.fuel_buddy.user.data.model.GetOrderedProductsDto
import com.nifa.fuel_buddy.user.domain.request.GetAllProductRequest
import com.nifa.fuel_buddy.user.domain.request.GetNearbyFuelStationRequest
import com.nifa.fuel_buddy.user.domain.request.GetOrderedProductRequest
import com.nifa.fuel_buddy.user.domain.request.OrderProductsRequest
import com.nifa.fuel_buddy.user.domain.request.TrackOrderRequest
import kotlinx.coroutines.flow.Flow

interface UserNetworkSource {

    suspend fun getNearbyFuelStation(request: GetNearbyFuelStationRequest): Flow<Result<GetNearbyFuelStationDto, NetworkError>>

    suspend fun getFuelOrderHistory(): Flow<Result<GetFuelOrderHistoryDto, NetworkError>>

    suspend fun getAllProducts(request: GetAllProductRequest): Flow<Result<GetAllProductsDto, NetworkError>>

    suspend fun getOrderedProducts(request: GetOrderedProductRequest): Flow<Result<GetOrderedProductsDto, NetworkError>>

    suspend fun trackOrder(request: TrackOrderRequest): Flow<Result<LatLong, NetworkError>>

    suspend fun orderResponse(): Flow<OrderDecision>

    suspend fun orderProducts(request: OrderProductsRequest): Flow<Result<SuccessResponse, NetworkError>>
}