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
        name = "Petrol",
        imageUrl = "",
        price = 102
    ),
    Product(
        name = "Diesel",
        imageUrl = "",
        price = 95
    ),
    Product(
        name = "Engine Oil",
        imageUrl = "",
        price = 500
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

val dummyFuelOrderHistory = listOf(
    FuelOrderHistory(
        orderId = "1",
        orderDateTime = "20 Oct | 1.41 PM",
        price = "690",
        fuelStation = hpFuelStation
    ),
    FuelOrderHistory(
        orderId = "2",
        orderDateTime = "1 Oct | 10.03 PM",
        price = "550",
        fuelStation = bharathFuelStation
    ),
    FuelOrderHistory(
        orderId = "3",
        orderDateTime = "12 Sept | 12.41 PM",
        price = "220",
        fuelStation = indianFuelStation
    ),
)

