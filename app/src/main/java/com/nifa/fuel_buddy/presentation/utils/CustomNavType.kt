package com.nifa.fuel_buddy.presentation.utils

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.nifa.fuel_buddy.domain.FuelStation
import com.nifa.fuel_buddy.domain.Product
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object CustomNavType {

    val FuelStationType = object : NavType<FuelStation>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): FuelStation? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): FuelStation =
            Json.decodeFromString(Uri.decode(value))

        override fun serializeAsValue(value: FuelStation): String =
            Uri.encode(Json.encodeToString(value))

        override fun put(bundle: Bundle, key: String, value: FuelStation) =
            bundle.putString(key, Json.encodeToString(value))
    }

    val ProductType = object : NavType<Product>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Product? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Product =
            Json.decodeFromString(Uri.decode(value))

        override fun serializeAsValue(value: Product): String =
            Uri.encode(Json.encodeToString(value))

        override fun put(bundle: Bundle, key: String, value: Product) =
            bundle.putString(key, Json.encodeToString(value))
    }
}