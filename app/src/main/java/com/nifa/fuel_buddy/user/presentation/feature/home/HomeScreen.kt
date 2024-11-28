package com.nifa.fuel_buddy.user.presentation.feature.home

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
import com.nifa.fuel_buddy.user.data.networkSource.dummyFuelStationList
import com.nifa.fuel_buddy.user.presentation.feature.home.components.HomeScreenCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeScreenUiState,
    uiAction: ((HomeScreenUiAction) -> Unit),
    uiEvent: Flow<HomeScreenUiEvent>,
    navigateToCallback: ((NavigationScreen) -> Unit)
) {

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is HomeScreenUiEvent.NavigateTo -> navigateToCallback.invoke(event.navigationScreen)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentPadding = PaddingValues(30.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        items(uiState.fuelStationList.size) { index ->
            val data = uiState.fuelStationList[index]
            HomeScreenCard(
                title = data.name,
                distance = data.distance,
                imageUrl = data.imageUrl,
                ratings = data.rating,
                ratingCount = data.ratedUserCount,
                onClick = {
                    uiAction.invoke(HomeScreenUiAction.OnFuelStationCardClicked(data))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeScreenUiState(
            fuelStationList = dummyFuelStationList
        ),
        uiAction = {},
        uiEvent = emptyFlow(),
        navigateToCallback = {}
    )
}