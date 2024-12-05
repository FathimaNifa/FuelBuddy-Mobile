package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.nifa.fuel_buddy.core.utils.CustomNavType
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.FuelStationNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class LiveOrderDetailScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    init {
        val data = savedStateHandle.toRoute<FuelStationNavigation.LiveOrderDetailScreen>(
            typeMap = mapOf(
                typeOf<OrderDetails>() to CustomNavType.OrderDetailsType,
            )
        )
    }
}