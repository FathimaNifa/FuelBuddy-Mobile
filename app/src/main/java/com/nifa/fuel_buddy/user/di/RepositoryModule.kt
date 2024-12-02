package com.nifa.fuel_buddy.user.di

import com.nifa.fuel_buddy.user.data.networkSource.FakeUserNetworkSource
import com.nifa.fuel_buddy.user.data.networkSource.UserNetworkSource
import com.nifa.fuel_buddy.user.data.repository.UserRepositoryImpl
import com.nifa.fuel_buddy.user.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    fun bindUserNetworkSource(userNetworkSource: FakeUserNetworkSource): UserNetworkSource
}