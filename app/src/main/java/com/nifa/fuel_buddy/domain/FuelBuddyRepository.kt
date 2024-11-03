package com.nifa.fuel_buddy.domain

interface FuelBuddyRepository {
    suspend fun getNearbyFuelStation(): List<FuelStation>
}