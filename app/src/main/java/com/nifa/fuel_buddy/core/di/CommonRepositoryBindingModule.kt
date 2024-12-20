package com.nifa.fuel_buddy.core.di

import com.nifa.fuel_buddy.core.data.networkSource.CommonNetworkSource
import com.nifa.fuel_buddy.core.data.networkSource.FakeCommonNetworkSource
import com.nifa.fuel_buddy.core.data.repository.CommonRepositoryImpl
import com.nifa.fuel_buddy.core.domain.CommonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CommonRepositoryBindingModule {

    @Singleton
    @Binds
    fun bindCommonNetworkSource(commonNetworkSource: FakeCommonNetworkSource): CommonNetworkSource

    @Singleton
    @Binds
    fun bindCommonRepository(commonRepository: CommonRepositoryImpl): CommonRepository
}