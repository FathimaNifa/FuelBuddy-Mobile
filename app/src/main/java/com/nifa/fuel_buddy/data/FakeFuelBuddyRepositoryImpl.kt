package com.nifa.fuel_buddy.data

import com.nifa.fuel_buddy.domain.FuelBuddyRepository
import com.nifa.fuel_buddy.domain.FuelOrderHistory
import com.nifa.fuel_buddy.domain.FuelStation
import com.nifa.fuel_buddy.domain.Product

class FakeFuelBuddyRepositoryImpl : FuelBuddyRepository {
    override suspend fun getNearbyFuelStation(): List<FuelStation> {
        return dummyFuelStationList
    }

    override suspend fun getFuelOrderHistory(): List<FuelOrderHistory> {
        return dummyFuelOrderHistory
    }
}

val dummyProductList = listOf(
    Product(
        productId = 0,
        name = "Petrol",
        imageUrl = "",
        price = 102,
        quantityAdded = 0
    ),
    Product(
        productId = 1,
        name = "Diesel",
        imageUrl = "",
        price = 95,
        quantityAdded = 0
    ),
    Product(
        productId = 3,
        name = "Engine Oil",
        imageUrl = "",
        price = 500,
        quantityAdded = 0
    )
)

val dummyProductListWithAddedQuantity = listOf(
    Product(
        productId = 0,
        name = "Petrol",
        imageUrl = "",
        price = 102,
        quantityAdded = 2
    ),
    Product(
        productId = 1,
        name = "Diesel",
        imageUrl = "",
        price = 95,
        quantityAdded = 1
    ),
    Product(
        productId = 3,
        name = "Engine Oil",
        imageUrl = "",
        price = 500,
        quantityAdded = 3
    )
)

val hpFuelStation = FuelStation(
    name = "HP Petrol",
    imageUrl = "",
    distance = "1.2 Km away",
    deliveryCharge = 50,
    productList = dummyProductList
)

val bharathFuelStation = FuelStation(
    name = "Bharath Petroleum",
    imageUrl = "",
    distance = "2.2 Km away",
    deliveryCharge = 70,
    productList = dummyProductList
)

val indianFuelStation = FuelStation(
    name = "Indian Oil",
    imageUrl = "",
    distance = "500 m away",
    deliveryCharge = 20,
    productList = dummyProductList
)

val dummyFuelStationList = listOf(
    hpFuelStation,
    bharathFuelStation,
    indianFuelStation
)

val quantityAddedFuelStationList = listOf(
    hpFuelStation.copy(productList = dummyProductListWithAddedQuantity),
    bharathFuelStation.copy(productList = dummyProductListWithAddedQuantity),
    indianFuelStation.copy(productList = dummyProductListWithAddedQuantity)
)

val dummyFuelOrderHistory = listOf(
    FuelOrderHistory(
        orderId = "1",
        orderDateTime = "20 Oct | 1.41 PM",
        totalPrice = "690",
        fuelStation = hpFuelStation.copy(productList = dummyProductListWithAddedQuantity)
    ),
    FuelOrderHistory(
        orderId = "2",
        orderDateTime = "1 Oct | 10.03 PM",
        totalPrice = "550",
        fuelStation = bharathFuelStation.copy(productList = dummyProductListWithAddedQuantity)
    ),
    FuelOrderHistory(
        orderId = "3",
        orderDateTime = "12 Sept | 12.41 PM",
        totalPrice = "220",
        fuelStation = indianFuelStation.copy(productList = dummyProductListWithAddedQuantity)
    ),
)

