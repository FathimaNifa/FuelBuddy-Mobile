package com.nifa.fuel_buddy.user.data.networkSource

import com.google.gson.Gson
import com.nifa.fuel_buddy.core.data.socket.SocketEvent
import com.nifa.fuel_buddy.core.data.socket.SocketIoManager
import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.domain.model.LatLong
import com.nifa.fuel_buddy.core.utils.ext.nullAsEmpty
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDecision
import com.nifa.fuel_buddy.user.domain.model.OrderResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserSocketSource @Inject constructor(
    private val socketIoManager: SocketIoManager
) {

    fun trackOrder() = callbackFlow<Result<LatLong, NetworkError>> {

        socketIoManager.socket?.on(SocketEvent.TRACK_ORDER.event) {
            val payload = (it?.get(0) as? String).nullAsEmpty()
            val data = Gson().fromJson(payload, LatLong::class.java)

            launch {
                send(
                    Result.Success(data)
                )
            }
        }

        awaitClose {
            socketIoManager.socket?.off(SocketEvent.TRACK_ORDER.event)
        }
    }

    fun orderResponse() = callbackFlow {
        socketIoManager.socket?.on(SocketEvent.ORDER_RESPONSE.event) {
            val payload = (it?.get(0) as? String).nullAsEmpty()
            val data = Gson().fromJson(payload, OrderResponse::class.java)
            val orderDecision = OrderDecision.entries.firstOrNull { it.name == data.type } ?: OrderDecision.ORDERED

            launch {
                send(orderDecision)
            }
        }
    }
}