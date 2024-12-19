package com.nifa.fuel_buddy.user.presentation.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import com.nifa.fuel_buddy.user.presentation.navigation.UserNavigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun OrderStatusScreen(
    modifier: Modifier = Modifier,
    uiState: OrderStatusScreenUiState,
    uiAction: (OrderStatusScreenUiAction) -> Unit,
    uiEvent: Flow<OrderStatusUiEvent>,
    navigateAndPopupCallback: (UserNavigation) -> Unit,
    navigateToCallback: (UserNavigation) -> Unit,
) {

    uiEvent.CollectAsEffect { event ->
        when(event){
            is OrderStatusUiEvent.NavigateAndPopupToCallback -> navigateAndPopupCallback.invoke(event.screen)
            is OrderStatusUiEvent.NavigateToCallback -> navigateToCallback.invoke(event.screen)
        }
    }

    BackHandler { }


    when (uiState.screenState) {
        OrderStatusScreenState.LOADING -> {
            LoaderScreenContent(modifier)
        }

        OrderStatusScreenState.APPROVED -> {
            OrderApprovedScreenContent(
                modifier = modifier,
                trackOrderButtonClicked = { uiAction.invoke(OrderStatusScreenUiAction.OnTrackOrderButtonClicked) },
                backToHomeButtonClicked = { uiAction.invoke(OrderStatusScreenUiAction.OnGoBackToHomeButtonClicked) }
            )
        }

        OrderStatusScreenState.DECLINED -> {
            OrderDeclinedScreenContent(
                modifier = modifier,
                backToHomeButtonClicked = { uiAction.invoke(OrderStatusScreenUiAction.OnGoBackToHomeButtonClicked) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderStatusScreenPreview() {
    OrderStatusScreen(
        uiState = OrderStatusScreenUiState(),
        uiAction = {},
        uiEvent = emptyFlow(),
        navigateToCallback = {},
        navigateAndPopupCallback = {}
    )
}