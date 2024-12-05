package com.nifa.fuel_buddy.fuelstation.presentation.feature.history

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
import com.nifa.fuel_buddy.fuelstation.presentation.feature.history.component.HistoryCard
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.FuelStationNavigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    uiState: HistoryScreenUiState,
    uiEvent: Flow<HistoryScreenUiEvent>,
    uiAction: (HistoryScreenUiAction) -> Unit,
    navigateToCallback: (FuelStationNavigation) -> Unit
) {

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is HistoryScreenUiEvent.NavigateTo -> navigateToCallback.invoke(event.screen)
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

            HistoryCard(
                orderNumber = data.orderNumber,
                totalPrice = data.totalPrice,
                orderStatus = data.orderStatus,
                dateAndTime = data.dateAndTime,
                onClick = {
                    uiAction.invoke(HistoryScreenUiAction.OnCardClicked(data))
                }
            )
        }
    }
}

@Preview
@Composable
private fun HistoryScreenPreview() {
    HistoryScreen(
        uiState = HistoryScreenUiState(),
        uiEvent = emptyFlow(),
        uiAction = {},
        navigateToCallback = {}
    )
}