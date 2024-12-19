package com.nifa.fuel_buddy.core.data.socket

import kotlinx.coroutines.flow.StateFlow

interface SocketIoManager {

    val socketIoConnectionState: StateFlow<SocketIoConnectionState>

    fun connect(bearerToken: String)

    fun disconnect()

    fun isDead(): Boolean

    fun isConnected(): Boolean
}