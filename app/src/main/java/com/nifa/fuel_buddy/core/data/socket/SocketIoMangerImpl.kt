package com.nifa.fuel_buddy.core.data.socket

import com.nifa.fuel_buddy.BuildConfig
import com.nifa.fuel_buddy.core.domain.util.NetworkConstant.AUTHORIZATION
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.net.URI
import javax.inject.Inject

class SocketIoMangerImpl @Inject constructor() : SocketIoManager {

    private val _socketIoConnectionState = MutableStateFlow(SocketIoConnectionState.DISCONNECTED)

    override val socketIoConnectionState: StateFlow<SocketIoConnectionState> =
        _socketIoConnectionState.asStateFlow()


    override var socket: Socket? = null
        private set

    @Synchronized
    override fun connect(bearerToken: String) {

        if (socket == null) {

            updateSocketIoConnectionState(SocketIoConnectionState.CONNECTING)

            val options = IO.Options().also {
                it.extraHeaders = mapOf(AUTHORIZATION to listOf(bearerToken))
            }

            socket = IO.socket(URI.create(BuildConfig.socketUrl), options).connect()

            setupConnectionEvents()
        }
    }

    @Synchronized
    override fun disconnect() {
        socket?.let {
            it.off()
            it.close()
            socket = null
            updateSocketIoConnectionState(SocketIoConnectionState.DISCONNECTING)
        }
    }

    @Synchronized
    override fun isDead(): Boolean = socket == null

    @Synchronized
    override fun isConnected(): Boolean = socket != null && socket!!.connected()

    private fun updateSocketIoConnectionState(state: SocketIoConnectionState): Unit =
        _socketIoConnectionState.update { state }

    private fun setupConnectionEvents() {
        socket?.let {
            it.on(Socket.EVENT_CONNECT, connectHandler)
            it.on(Socket.EVENT_DISCONNECT, disconnectHandler)
            it.on(Socket.EVENT_CONNECT_ERROR, errorHandler)
        }
    }

    private val connectHandler = Emitter.Listener {
        updateSocketIoConnectionState(SocketIoConnectionState.CONNECTED)
    }

    private val disconnectHandler = Emitter.Listener {
        if (socket != null) {
            updateSocketIoConnectionState(SocketIoConnectionState.RECONNECTING)
        } else {
            updateSocketIoConnectionState(SocketIoConnectionState.DISCONNECTED)
        }
    }

    private val errorHandler = Emitter.Listener {
        updateSocketIoConnectionState(SocketIoConnectionState.RECONNECTING)
    }
}