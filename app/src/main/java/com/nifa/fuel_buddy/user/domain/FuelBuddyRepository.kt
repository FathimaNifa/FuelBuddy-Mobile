package com.nifa.fuel_buddy.user.domain

interface FuelBuddyRepository {

    suspend fun getNearbyFuelStation(): List<FuelStation>

    suspend fun getFuelOrderHistory(): List<FuelOrderHistory>
}