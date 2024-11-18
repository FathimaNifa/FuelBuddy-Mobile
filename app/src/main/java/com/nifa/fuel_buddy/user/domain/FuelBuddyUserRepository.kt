package com.nifa.fuel_buddy.user.domain

interface FuelBuddyUserRepository {

    suspend fun getNearbyFuelStation(): List<FuelStation>

    suspend fun getFuelOrderHistory(): List<FuelOrderHistory>
}