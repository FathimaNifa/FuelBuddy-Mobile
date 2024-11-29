package com.nifa.fuel_buddy.core.di

import com.nifa.fuel_buddy.core.domain.LocationClient
import com.nifa.fuel_buddy.core.domain.LocationClientImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocationBindingModule {
    @Binds
    fun bindLocationClient(locationClient: LocationClientImpl): LocationClient
}