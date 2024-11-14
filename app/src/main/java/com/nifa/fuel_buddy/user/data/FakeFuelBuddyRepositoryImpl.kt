package com.nifa.fuel_buddy.user.data

import com.nifa.fuel_buddy.user.domain.FuelBuddyRepository
import com.nifa.fuel_buddy.user.domain.FuelOrderHistory
import com.nifa.fuel_buddy.user.domain.FuelStation
import com.nifa.fuel_buddy.user.domain.Product

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
        imageUrl = "https://fuel-buddy-backend.vercel.app/assets/petrol_1.png",
        price = 102,
        quantityAdded = 0
    ),
    Product(
        productId = 1,
        name = "Diesel",
        imageUrl = "https://fuel-buddy-backend.vercel.app/assets/diesel_1.png",
        price = 95,
        quantityAdded = 0
    ),
    Product(
        productId = 3,
        name = "Engine Oil",
        imageUrl = "https://fuel-buddy-backend.vercel.app/assets/engine_oil_1.png",
        price = 500,
        quantityAdded = 0
    ),
    Product(
        productId = 4,
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
    name = "HP Petrol",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/hp_16.png",
    distance = "1.2 Km away",
    deliveryCharge = 50,
    productList = dummyProductList
)

val bharathFuelStation = FuelStation(
    name = "Bharath Petroleum",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/bharat_16.png",
    distance = "2.2 Km away",
    deliveryCharge = 70,
    productList = dummyProductList
)

val indianFuelStation = FuelStation(
    name = "Indian Oil",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/indian-oil_16.png",
    distance = "500 m away",
    deliveryCharge = 20,
    productList = dummyProductList
)

val nayara = FuelStation(
    name = "Nayara",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/nayara_16.png",
    distance = "3.3 Km away",
    deliveryCharge = 90,
    productList = dummyProductList
)

val reliance = FuelStation(
    name = "Reliance",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/reliance_16.png",
    distance = "1 Km away",
    deliveryCharge = 40,
    productList = dummyProductList
)

val shell = FuelStation(
    name = "Shell",
    imageUrl = "https://fuel-buddy-backend.vercel.app/assets/shell_16.png",
    distance = "5 Km away",
    deliveryCharge = 110,
    productList = dummyProductList
)

val dummyFuelStationList = listOf(
    hpFuelStation,
    bharathFuelStation,
    indianFuelStation,
    reliance,
    shell,
    nayara
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
        totalPrice = "2243",
        fuelStation = hpFuelStation.copy(productList = dummyProductListWithAddedQuantity)
    ),
    FuelOrderHistory(
        orderId = "2",
        orderDateTime = "1 Oct | 10.03 PM",
        totalPrice = "2263",
        fuelStation = bharathFuelStation.copy(productList = dummyProductListWithAddedQuantity)
    ),
    FuelOrderHistory(
        orderId = "3",
        orderDateTime = "12 Sept | 12.41 PM",
        totalPrice = "2213",
        fuelStation = indianFuelStation.copy(productList = dummyProductListWithAddedQuantity)
    ),
    FuelOrderHistory(
        orderId = "4",
        orderDateTime = "9 Nov | 6.39 AM",
        totalPrice = "2303",
        fuelStation = shell.copy(productList = dummyProductListWithAddedQuantity)
    ),
    FuelOrderHistory(
        orderId = "5",
        orderDateTime = "1 Feb | 9.41 PM",
        totalPrice = "2233",
        fuelStation = reliance.copy(productList = dummyProductListWithAddedQuantity)
    ),
    FuelOrderHistory(
        orderId = "6",
        orderDateTime = "9 Mar | 2.05 PM",
        totalPrice = "2283",
        fuelStation = nayara.copy(productList = dummyProductListWithAddedQuantity)
    ),
)

