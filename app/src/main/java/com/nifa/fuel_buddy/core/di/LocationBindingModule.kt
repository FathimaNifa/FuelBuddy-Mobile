package com.nifa.fuel_buddy.core.di

import com.nifa.fuel_buddy.core.data.location.FakeLocationClientImpl
import com.nifa.fuel_buddy.core.domain.location.LocationClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocationBindingModule {
    @Binds
    fun bindLocationClient(locationClient: FakeLocationClientImpl): LocationClient
}