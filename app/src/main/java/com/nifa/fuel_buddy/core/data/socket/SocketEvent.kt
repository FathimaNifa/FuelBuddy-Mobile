package com.nifa.fuel_buddy.core.data.socket

enum class SocketEvent(val event: String) {
    NEW_CUSTOMER_ORDERS("_newCustomerOrders"),
    TRACK_ORDER("_trackOrder"),
}