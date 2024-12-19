package com.nifa.fuel_buddy.user.data.networkSource

import com.nifa.fuel_buddy.user.data.model.GetAllProductsDto
import com.nifa.fuel_buddy.user.data.model.GetFuelOrderHistoryDto
import com.nifa.fuel_buddy.user.data.model.GetNearbyFuelStationDto
import com.nifa.fuel_buddy.user.data.model.GetOrderedProductsDto
import com.nifa.fuel_buddy.user.data.model.OrderProductsDto
import com.nifa.fuel_buddy.user.domain.request.GetAllProductRequest
import com.nifa.fuel_buddy.user.domain.request.GetNearbyFuelStationRequest
import com.nifa.fuel_buddy.user.domain.request.GetOrderedProductRequest
import com.nifa.fuel_buddy.user.domain.request.OrderProductsRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("bunk/getNearestBunkDetails")
    suspend fun getNearbyFuelStation(@Body request: GetNearbyFuelStationRequest): Response<GetNearbyFuelStationDto>

    @POST("bunk/getFuelOrderHistory")
    suspend fun getFuelOrderHistory(): Response<GetFuelOrderHistoryDto>

    @POST("product/getAllProducts")
    suspend fun getAllProducts(@Body request: GetAllProductRequest): Response<GetAllProductsDto>

    @POST("products/getOrderedProducts")
    suspend fun getOrderedProducts(@Body request: GetOrderedProductRequest): Response<GetOrderedProductsDto>

    @POST("product/orderProduct")
    suspend fun orderProducts(@Body request: OrderProductsRequest): Response<OrderProductsDto>
}