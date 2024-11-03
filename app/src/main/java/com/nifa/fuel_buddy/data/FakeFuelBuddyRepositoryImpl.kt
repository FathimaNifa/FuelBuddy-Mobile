package com.nifa.fuel_buddy.data

import com.nifa.fuel_buddy.domain.FuelBuddyRepository
import com.nifa.fuel_buddy.domain.FuelStation
import com.nifa.fuel_buddy.domain.Product

class FakeFuelBuddyRepositoryImpl : FuelBuddyRepository {
    override suspend fun getNearbyFuelStation(): List<FuelStation> {
        return dummyFuelStationList
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

val dummyFuelStationList = listOf(
    FuelStation(
        name = "HP Petrol",
        imageUrl = "",
        distance = "1.2 Km away",
        deliveryCharge = 50,
        productList = dummyProductList
    ),
    FuelStation(
        name = "Bharath Petroleum",
        imageUrl = "",
        distance = "2.2 Km away",
        deliveryCharge = 70,
        productList = dummyProductList
    ),
    FuelStation(
        name = "Indian Oil",
        imageUrl = "",
        distance = "500 m away",
        deliveryCharge = 20,
        productList = dummyProductList
    )
)

