package com.nifa.fuel_buddy.core.data.datastore.di

import com.nifa.fuel_buddy.core.data.datastore.common.PreferenceDataSource
import com.nifa.fuel_buddy.core.data.datastore.common.PreferenceDataSourceImpl
import com.nifa.fuel_buddy.core.data.datastore.fuelstation.FuelStationPreferenceDataSource
import com.nifa.fuel_buddy.core.data.datastore.fuelstation.FuelStationPreferenceDataSourceImpl
import com.nifa.fuel_buddy.core.data.datastore.user.UserPreferenceDataSource
import com.nifa.fuel_buddy.core.data.datastore.user.UserPreferenceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreBindingModule {

    @Binds
    fun bindFuelStationDataStoreSource(fuelStationPreferenceDataSource: FuelStationPreferenceDataSourceImpl): FuelStationPreferenceDataSource

    @Binds
    fun bindUserDataStoreSource(userPreferenceDataSource: UserPreferenceDataSourceImpl): UserPreferenceDataSource

    @Binds
    fun bindDataStoreSource(preferenceDataSource: PreferenceDataSourceImpl): PreferenceDataSource
}