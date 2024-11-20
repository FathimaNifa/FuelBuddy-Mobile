package com.nifa.fuel_buddy.auth.di

import com.nifa.fuel_buddy.auth.data.networkSource.AuthNetworkSource
import com.nifa.fuel_buddy.auth.data.networkSource.AuthNetworkSourceImpl
import com.nifa.fuel_buddy.auth.data.repository.AuthRepositoryImpl
import com.nifa.fuel_buddy.auth.domain.AuthRepository
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
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindAuthNetworkSource(authNetworkSourceImpl: AuthNetworkSourceImpl) : AuthNetworkSource
}