package com.nifa.fuel_buddy.fuelstation.di

import com.nifa.fuel_buddy.fuelstation.data.networkSource.FakeFuelStationNetworkSource
import com.nifa.fuel_buddy.fuelstation.data.networkSource.FuelStationNetworkSource
import com.nifa.fuel_buddy.fuelstation.data.repository.FuelStationRepositoryImpl
import com.nifa.fuel_buddy.fuelstation.domain.FuelStationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindFuelStationRepository(fuelStationRepositoryImpl: FuelStationRepositoryImpl) : FuelStationRepository

    @Binds
    @Singleton
    fun bindFuelStationNetworkSource(fuelStationNetworkSource: FakeFuelStationNetworkSource) : FuelStationNetworkSource
}