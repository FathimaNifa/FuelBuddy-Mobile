package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.components.LiveOrderCard
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.FuelStationNavigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun LiveOrderScreen(
    modifier: Modifier = Modifier,
    uiState: LiveOrderScreenUiState,
    uiAction: (LiveOrderScreenUiAction) -> Unit,
    uiEvent: Flow<LiveOrderScreenUiEvent>,
    navigateToCallback: (FuelStationNavigation) -> Unit
) {

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is LiveOrderScreenUiEvent.NavigateTo -> navigateToCallback.invoke(event.screen)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentPadding = PaddingValues(30.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        items(uiState.orderList.size) { index ->
            val data = uiState.orderList[index]

            LiveOrderCard(
                orderNumber = data.orderNumber,
                location = data.location,
                awayFrom = data.awayFrom,
                totalPrice = data.totalPrice,
                onAcceptButtonClicked = {
                    uiAction.invoke(
                        LiveOrderScreenUiAction.OnAcceptButtonClicked(
                            orderId = data.orderId,
                            latitude = data.latitude,
                            longitude = data.longitude
                        )
                    )
                },
                onClick = { uiAction.invoke(LiveOrderScreenUiAction.OnCardClicked(data)) },
                onDeclineButtonClicked = {
                    uiAction.invoke(
                        LiveOrderScreenUiAction.OnDeclineButtonClicked(
                            data.orderId
                        )
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun LiveOrderScreenPreview() {
    LiveOrderScreen(
        uiState = LiveOrderScreenUiState(),
        uiAction = {},
        uiEvent = emptyFlow(),
        navigateToCallback = {}
    )
}