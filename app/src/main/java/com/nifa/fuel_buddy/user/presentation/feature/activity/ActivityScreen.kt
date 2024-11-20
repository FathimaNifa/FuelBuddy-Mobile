package com.nifa.fuel_buddy.user.presentation.feature.activity

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
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import com.nifa.fuel_buddy.user.data.dummyFuelOrderHistory
import com.nifa.fuel_buddy.user.presentation.feature.activity.components.ActivityCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ActivityScreen(
    modifier: Modifier = Modifier,
    uiState: ActivityScreenUiState,
    uiEvent: Flow<ActivityScreenUiEvent>,
    uiAction: (ActivityScreenUiAction) -> Unit,
    navigateToCallback: (NavigationScreen) -> Unit
) {

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is ActivityScreenUiEvent.NavigateTo -> navigateToCallback.invoke(event.navigationScreen)
        }
    }


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentPadding = PaddingValues(30.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        items(uiState.fuelOrderHistoryList.size) { index ->
            val data = uiState.fuelOrderHistoryList[index]
            ActivityCard(
                price = data.totalPrice,
                title = data.fuelStation.name,
                datetime = data.orderDateTime,
                imageUrl = data.fuelStation.imageUrl,
                onClick = {
                    uiAction.invoke(ActivityScreenUiAction.OnActivityCardClicked(data.fuelStation))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityScreenPreview() {
    ActivityScreen(
        uiState = ActivityScreenUiState(
            fuelOrderHistoryList = dummyFuelOrderHistory
        ),
        uiAction = {},
        uiEvent = emptyFlow(),
        navigateToCallback = {}
    )
}