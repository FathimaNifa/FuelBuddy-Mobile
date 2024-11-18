package com.nifa.fuel_buddy.auth.di

import com.nifa.fuel_buddy.auth.data.FakeAuthRepository
import com.nifa.fuel_buddy.auth.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAuthRepository(fakeAuthRepository: FakeAuthRepository) : AuthRepository
}