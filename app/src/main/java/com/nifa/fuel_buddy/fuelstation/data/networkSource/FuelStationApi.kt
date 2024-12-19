package com.nifa.fuel_buddy.fuelstation.data.networkSource

import com.nifa.fuel_buddy.core.domain.SuccessResponse
import com.nifa.fuel_buddy.fuelstation.data.model.GetCustomerOrderDto
import com.nifa.fuel_buddy.fuelstation.data.model.GetOrderedProductDetailsDto
import com.nifa.fuel_buddy.fuelstation.domain.model.request.AcceptOrderRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetCustomerOrdersRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderHistoryRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.GetOrderedProductsRequest
import com.nifa.fuel_buddy.fuelstation.domain.model.request.UpdateDriverLocationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FuelStationApi {

    @POST("bunk/getNewOrders")
    suspend fun getCustomerOrders(@Body request: GetCustomerOrdersRequest): Response<GetCustomerOrderDto>

    @POST("bunk/getOrderHistory")
    suspend fun getOrderHistory(@Body request: GetOrderHistoryRequest): Response<GetCustomerOrderDto>

    @POST("product/getOrderedProducts")
    suspend fun getOrderedProducts(@Body request: GetOrderedProductsRequest): Response<GetOrderedProductDetailsDto>

    @POST("user/updateLocation")
    suspend fun updateLocation(@Body request: UpdateDriverLocationRequest): Response<SuccessResponse>

    @POST("bunk/acceptOrder")
    suspend fun acceptOrder(@Body request: AcceptOrderRequest): Response<SuccessResponse>
}