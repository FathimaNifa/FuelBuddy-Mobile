package com.nifa.fuel_buddy.core.data.socket

import io.socket.client.Socket
import kotlinx.coroutines.flow.StateFlow

interface SocketIoManager {

    val socketIoConnectionState: StateFlow<SocketIoConnectionState>

    val socket: Socket?

    fun connect(bearerToken: String)

    fun disconnect()

    fun isDead(): Boolean

    fun isConnected(): Boolean
}