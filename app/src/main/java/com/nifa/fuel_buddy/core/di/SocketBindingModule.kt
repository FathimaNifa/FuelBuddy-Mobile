package com.nifa.fuel_buddy.core.di

import com.nifa.fuel_buddy.core.data.socket.SocketIoManager
import com.nifa.fuel_buddy.core.data.socket.SocketIoMangerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SocketBindingModule {
    @Binds
    @Singleton
    fun bindSocketIoManager(socketIoMangerImpl: SocketIoMangerImpl): SocketIoManager
}