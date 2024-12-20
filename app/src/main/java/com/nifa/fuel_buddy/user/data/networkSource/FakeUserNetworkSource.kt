package com.nifa.fuel_buddy.user.data.networkSource

import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.domain.model.LatLong
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDecision
import com.nifa.fuel_buddy.user.data.model.GetAllProductsDto
import com.nifa.fuel_buddy.user.data.model.GetFuelOrderHistoryDto
import com.nifa.fuel_buddy.user.data.model.GetNearbyFuelStationDto
import com.nifa.fuel_buddy.user.data.model.GetOrderedProductsDto
import com.nifa.fuel_buddy.user.data.model.OrderProductsDataDto
import com.nifa.fuel_buddy.user.data.model.OrderProductsDto
import com.nifa.fuel_buddy.user.data.model.toFuelOrderHistoryDto
import com.nifa.fuel_buddy.user.data.model.toFuelStationDto
import com.nifa.fuel_buddy.user.data.model.toOrderedProductsDto
import com.nifa.fuel_buddy.user.data.model.toProductDto
import com.nifa.fuel_buddy.user.domain.model.FuelOrderHistory
import com.nifa.fuel_buddy.user.domain.model.FuelStation
import com.nifa.fuel_buddy.user.domain.model.Product
import com.nifa.fuel_buddy.user.domain.request.GetAllProductRequest
import com.nifa.fuel_buddy.user.domain.request.GetNearbyFuelStationRequest
import com.nifa.fuel_buddy.user.domain.request.GetOrderedProductRequest
import com.nifa.fuel_buddy.user.domain.request.OrderProductsRequest
import com.nifa.fuel_buddy.user.domain.request.TrackOrderRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeUserNetworkSource @Inject constructor() : UserNetworkSource {
    override suspend fun getNearbyFuelStation(request: GetNearbyFuelStationRequest): Flow<Result<GetNearbyFuelStationDto, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            emit(
                Result.Success(
                    GetNearbyFuelStationDto(
                        statusCode = 200,
                        message = "Success",
                        data = dummyFuelStationList.map(FuelStation::toFuelStationDto)
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun getFuelOrderHistory(): Flow<Result<GetFuelOrderHistoryDto, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            emit(
                Result.Success(
                    GetFuelOrderHistoryDto(
                        statusCode = 200,
                        message = "Success",
                        data = dummyFuelOrderHistory.map(FuelOrderHistory::toFuelOrderHistoryDto)
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun getAllProducts(request: GetAllProductRequest): Flow<Result<GetAllProductsDto, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            emit(
                Result.Success(
                    GetAllProductsDto(
                        statusCode = 200,
                        message = "Success",
                        data = dummyProductList.map(Product::toProductDto)
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun getOrderedProducts(request: GetOrderedProductRequest): Flow<Result<GetOrderedProductsDto, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            emit(
                Result.Success(
                    GetOrderedProductsDto(
                        statusCode = 200,
                        message = "Success",
                        data = dummyProductListWithAddedQuantity.map(Product::toOrderedProductsDto)
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }

    override suspend fun trackOrder(request: TrackOrderRequest): Flow<Result<LatLong, NetworkError>> {
        return flow {
            getRoute().forEach { latLng ->
                emit(
                    Result.Success(
                        latLng
                    )
                )
                delay(800)
            }
        }
    }

    override suspend fun orderResponse(): Flow<OrderDecision> {
        return flow {
            emit(OrderDecision.ORDERED)
            delay(2000L)
            emit(OrderDecision.ACCEPTED)
        }
    }

    override suspend fun orderProducts(request: OrderProductsRequest): Flow<Result<OrderProductsDto, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            emit(
                Result.Success(
                    OrderProductsDto(
                        statusCode = 200,
                        message = "Success",
                        data = OrderProductsDataDto(
                            orderId = "1234567"
                        )
                    )
                )
            )
            emit(Result.Loading(false))
        }
    }
}

val dummyProductList = listOf(
    Product(
        productId = "0",
        name = "Petrol",
        imageUrl = "https://fuel-buddy-backend.vercel.app/assets/petrol_1.png",
        price = 102,
        quantityAdded = 0
    ),
    Product(
        productId = "1",
        name = "Diesel",
        imageUrl = "https://fuel-buddy-backend.vercel.app/assets/diesel_1.png",
        price = 95,
        quantityAdded = 0
    ),
    Product(
        productId = "3",
        name = "Engine Oil",
        imageUrl = "https://fuel-buddy-backend.vercel.app/assets/engine_oil_1.png",
        price = 500,
        quantityAdded = 0
    ),
    Product(
        productId = "4",
        name = "Shell Engine Oil",
        imageUrl = "https://fuel-buddy-backend.vercel.app/assets/shell_engine_oil_1.png",
        price = 500,
        quantityAdded = 0
    )
)

val dummyProductListWithAddedQuantity =
    dummyProductList.mapIndexed { index, product ->
        product.copy(
            quantityAdded = dummyProductList.size - index
        )
    }

val hpFuelStation = FuelStation(
    id = "0",
    name = "HP Petrol",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/hp_16.png",
    distance = "1.2",
    deliveryCharge = 50,
    rating = 4,
    ratedUserCount = 23
)

val bharathFuelStation = FuelStation(
    id = "1",
    name = "Bharath Petroleum",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/bharat_16.png",
    distance = "2.2",
    deliveryCharge = 70,
    rating = 3,
    ratedUserCount = 35
)

val indianFuelStation = FuelStation(
    id = "2",
    name = "Indian Oil",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/indian-oil_16.png",
    distance = "500",
    deliveryCharge = 20,
    rating = 4,
    ratedUserCount = 45
)

val nayara = FuelStation(
    id = "3",
    name = "Nayara",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/nayara_16.png",
    distance = "3.3",
    deliveryCharge = 90,
    rating = 5,
    ratedUserCount = 2
)

val reliance = FuelStation(
    id = "4",
    name = "Reliance",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/reliance_16.png",
    distance = "1",
    deliveryCharge = 40,
    rating = 5,
    ratedUserCount = 21
)

val shell = FuelStation(
    id = "5",
    name = "Shell",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/shell_16.png",
    distance = "5",
    deliveryCharge = 110,
    rating = 4,
    ratedUserCount = 50
)

val dummyFuelStationList = listOf(
    hpFuelStation,
    bharathFuelStation,
    indianFuelStation,
    reliance,
    shell,
    nayara
)

val quantityAddedFuelStationList = dummyProductListWithAddedQuantity

val dummyFuelOrderHistory = listOf(
    FuelOrderHistory(
        orderId = "1",
        orderDateTime = "20 Oct | 1.41 PM",
        totalPrice = "2243",
        bunkId = hpFuelStation.id,
        bunkName = hpFuelStation.name,
        bunkImage = hpFuelStation.imageUrl,
        bunkRating = hpFuelStation.rating,
        bunkRatedUserCount = hpFuelStation.ratedUserCount,
        deliveryCharge = hpFuelStation.deliveryCharge,
        awayFrom = hpFuelStation.distance.toDouble()
//        productList = dummyProductListWithAddedQuantity
    ),
    FuelOrderHistory(
        orderId = "2",
        orderDateTime = "1 Oct | 10.03 PM",
        totalPrice = "2263",
        bunkId = bharathFuelStation.id,
        bunkName = bharathFuelStation.name,
        bunkImage = bharathFuelStation.imageUrl,
        bunkRating = bharathFuelStation.rating,
        bunkRatedUserCount = bharathFuelStation.ratedUserCount,
        deliveryCharge = bharathFuelStation.deliveryCharge,
        awayFrom = bharathFuelStation.distance.toDouble()
//        productList = dummyProductListWithAddedQuantity
    ),
    FuelOrderHistory(
        orderId = "3",
        orderDateTime = "12 Sept | 12.41 PM",
        totalPrice = "2213",
        bunkId = indianFuelStation.id,
        bunkName = indianFuelStation.name,
        bunkImage = indianFuelStation.imageUrl,
        bunkRating = indianFuelStation.rating,
        bunkRatedUserCount = indianFuelStation.ratedUserCount,
        deliveryCharge = indianFuelStation.deliveryCharge,
        awayFrom = indianFuelStation.distance.toDouble()
//        productList = dummyProductListWithAddedQuantity
    ),
    FuelOrderHistory(
        orderId = "4",
        orderDateTime = "9 Nov | 6.39 AM",
        totalPrice = "2303",
        bunkId = reliance.id,
        bunkName = reliance.name,
        bunkImage = reliance.imageUrl,
        bunkRating = reliance.rating,
        bunkRatedUserCount = reliance.ratedUserCount,
        deliveryCharge = reliance.deliveryCharge,
        awayFrom = reliance.distance.toDouble()
//        productList = dummyProductListWithAddedQuantity
    ),
    FuelOrderHistory(
        orderId = "5",
        orderDateTime = "1 Feb | 9.41 PM",
        totalPrice = "2233",
        bunkId = shell.id,
        bunkName = shell.name,
        bunkImage = shell.imageUrl,
        bunkRating = shell.rating,
        bunkRatedUserCount = shell.ratedUserCount,
        deliveryCharge = shell.deliveryCharge,
        awayFrom = shell.distance.toDouble()
//        productList = dummyProductListWithAddedQuantity
    ),
    FuelOrderHistory(
        orderId = "6",
        orderDateTime = "9 Mar | 2.05 PM",
        totalPrice = "2283",
        bunkId = nayara.id,
        bunkName = nayara.name,
        bunkImage = nayara.imageUrl,
        bunkRating = nayara.rating,
        bunkRatedUserCount = nayara.ratedUserCount,
        deliveryCharge = nayara.deliveryCharge,
        awayFrom = nayara.distance.toDouble()
//        productList = dummyProductListWithAddedQuantity
    ),
)

fun getRoute() = listOf(
    LatLong(
        12.831517, 79.709842
    ), LatLong(
        12.831684, 79.709903
    ), LatLong(
        12.831748, 79.710025
    ), LatLong(
        12.831753, 79.710120
    ), LatLong(
        12.831685, 79.710226
    ), LatLong(
        12.831590, 79.710339
    ), LatLong(
        12.831496, 79.710446
    ), LatLong(
        12.831291, 79.710647
    ), LatLong(
        12.831209, 79.710719
    ), LatLong(
        12.831055, 79.710833
    ), LatLong(
        12.830982, 79.710879
    ), LatLong(
        12.830839, 79.711059
    ), LatLong(
        12.830773, 79.711165
    ), LatLong(
        12.830704, 79.711269
    ), LatLong(
        12.830643, 79.711358
    ), LatLong(
        12.830327, 79.711408
    ), LatLong(
        12.830187, 79.711386
    ), LatLong(
        12.829927, 79.711273
    ), LatLong(
        12.829758, 79.711219
    ), LatLong(
        12.829661, 79.711177
    ), LatLong(
        12.829537, 79.711136
    ), LatLong(
        12.829432, 79.711104
    ), LatLong(
        12.829329, 79.711072
    ), LatLong(
        12.829221, 79.711034
    ), LatLong(
        12.829073, 79.710996
    ), LatLong(
        12.828917, 79.710969
    ), LatLong(
        12.828770, 79.710958
    ), LatLong(
        12.828559, 79.710971
    ), LatLong(
        12.828446, 79.710986
    ), LatLong(
        12.828324, 79.711001
    ), LatLong(
        12.828039, 79.711029
    ), LatLong(
        12.827904, 79.711034
    ), LatLong(
        12.827760, 79.711040
    ), LatLong(
        12.827630, 79.711207
    ), LatLong(
        12.827587, 79.711329
    ), LatLong(
        12.827635, 79.711460
    ), LatLong(
        12.827743, 79.711588
    ), LatLong(
        12.827836, 79.711714
    ), LatLong(
        12.827894, 79.711838
    ), LatLong(
        12.827939, 79.711936
    ), LatLong(
        12.827968, 79.711978
    )
)