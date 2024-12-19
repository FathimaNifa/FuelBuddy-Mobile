package com.nifa.fuel_buddy.fuelstation.di

import com.nifa.fuel_buddy.fuelstation.data.networkSource.FuelStationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FuelStationModule {

    @Provides
    @Singleton
    fun provideFuelStationApi(retrofit: Retrofit) : FuelStationApi =
        retrofit.create()
}