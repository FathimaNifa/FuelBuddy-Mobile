package com.nifa.fuel_buddy.core.data.socket

enum class SocketIoConnectionState {
    CONNECTING,
    CONNECTED,
    RECONNECTING,
    DISCONNECTING,
    DISCONNECTED
}