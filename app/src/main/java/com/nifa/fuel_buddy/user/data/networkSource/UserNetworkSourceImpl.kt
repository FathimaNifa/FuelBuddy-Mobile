package com.nifa.fuel_buddy.user.data.networkSource

import android.content.Context
import com.nifa.fuel_buddy.core.domain.BaseApiResponse
import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.domain.model.LatLong
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDecision
import com.nifa.fuel_buddy.user.data.model.GetAllProductsDto
import com.nifa.fuel_buddy.user.data.model.GetFuelOrderHistoryDto
import com.nifa.fuel_buddy.user.data.model.GetNearbyFuelStationDto
import com.nifa.fuel_buddy.user.data.model.GetOrderedProductsDto
import com.nifa.fuel_buddy.user.data.model.OrderProductsDto
import com.nifa.fuel_buddy.user.domain.request.GetAllProductRequest
import com.nifa.fuel_buddy.user.domain.request.GetNearbyFuelStationRequest
import com.nifa.fuel_buddy.user.domain.request.GetOrderedProductRequest
import com.nifa.fuel_buddy.user.domain.request.OrderProductsRequest
import com.nifa.fuel_buddy.user.domain.request.TrackOrderRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserNetworkSourceImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val userApi: UserApi,
    private val userSocketSource: UserSocketSource
) : BaseApiResponse(context), UserNetworkSource {
    override suspend fun getNearbyFuelStation(request: GetNearbyFuelStationRequest): Flow<Result<GetNearbyFuelStationDto, NetworkError>> {
        return safeApiCall { userApi.getNearbyFuelStation(request) }
    }

    override suspend fun getFuelOrderHistory(): Flow<Result<GetFuelOrderHistoryDto, NetworkError>> {
        return safeApiCall { userApi.getFuelOrderHistory() }
    }

    override suspend fun getAllProducts(request: GetAllProductRequest): Flow<Result<GetAllProductsDto, NetworkError>> {
        return safeApiCall { userApi.getAllProducts(request) }
    }

    override suspend fun getOrderedProducts(request: GetOrderedProductRequest): Flow<Result<GetOrderedProductsDto, NetworkError>> {
        return safeApiCall { userApi.getOrderedProducts(request) }
    }

    override suspend fun trackOrder(request: TrackOrderRequest): Flow<Result<LatLong, NetworkError>> {
        return userSocketSource.trackOrder()
    }

    override suspend fun orderResponse(): Flow<OrderDecision> {
        return userSocketSource.orderResponse()
    }

    override suspend fun orderProducts(request: OrderProductsRequest): Flow<Result<OrderProductsDto, NetworkError>> {
        return safeApiCall { userApi.orderProducts(request) }
    }
}