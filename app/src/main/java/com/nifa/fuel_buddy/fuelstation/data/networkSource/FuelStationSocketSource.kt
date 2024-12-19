package com.nifa.fuel_buddy.fuelstation.data.networkSource

import com.google.gson.Gson
import com.nifa.fuel_buddy.core.data.socket.SocketEvent
import com.nifa.fuel_buddy.core.data.socket.SocketIoManager
import com.nifa.fuel_buddy.core.utils.ext.nullAsEmpty
import com.nifa.fuel_buddy.fuelstation.data.model.OrderDetailsDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FuelStationSocketSource @Inject constructor(
    private val socketIoManager: SocketIoManager
) {

    fun getCustomerOrders() = callbackFlow<OrderDetailsDto> {
        socketIoManager.socket?.on(SocketEvent.NEW_CUSTOMER_ORDERS.event) {
            val payload = (it?.get(0) as? String).nullAsEmpty()
            val data = Gson().fromJson(payload, OrderDetailsDto::class.java)

            launch {
                send(data)
            }
        }

        awaitClose {
            socketIoManager.socket?.off(SocketEvent.NEW_CUSTOMER_ORDERS.event)
        }
    }
}