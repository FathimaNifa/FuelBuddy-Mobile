package com.nifa.fuel_buddy.presentation.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nifa.fuel_buddy.data.dummyFuelStationList
import com.nifa.fuel_buddy.data.dummyProductList
import com.nifa.fuel_buddy.presentation.core.navigation.NavigationScreen
import com.nifa.fuel_buddy.presentation.utils.ext.CollectAsEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeDetailScreen(
    modifier: Modifier = Modifier,
    uiState: HomeDetailScreenUiState,
    uiAction: (HomeDetailUiAction) -> Unit,
    uiEvent: Flow<HomeDetailScreenUiEvent>,
    navigateToCallback : (NavigationScreen) -> Unit
) {

    uiEvent.CollectAsEffect { event ->

        when(event){
            is HomeDetailScreenUiEvent.NavigateTo -> navigateToCallback.invoke(event.navigationScreen)
        }
    }

    when(uiState.homeDetailScreenState){
        HomeDetailScreenState.DETAIL -> {
            HomeDetailContent(
                modifier = modifier,
                uiState = uiState,
                uiAction = uiAction
            )
        }
        HomeDetailScreenState.CART -> {
            HomeCartContent(
                modifier = modifier,
                uiState = uiState,
                uiAction = uiAction
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeDetailScreenPreview() {
    HomeDetailScreen(
        uiAction = { },
        uiEvent = emptyFlow(),
        uiState = HomeDetailScreenUiState(
            fuelStation = dummyFuelStationList[0],
            productList = dummyProductList,
            addedItemCount = 3,
            shouldShowCartCTABottomSheet = true
        ),
        navigateToCallback = {}
    )
}