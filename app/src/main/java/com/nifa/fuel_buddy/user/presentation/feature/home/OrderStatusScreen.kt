package com.nifa.fuel_buddy.user.presentation.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OrderStatusScreen(
    modifier: Modifier = Modifier,
    uiState: OrderStatusScreenUiState
) {

    when (uiState.screenState) {
        OrderStatusScreenState.LOADING -> {
            LoaderScreenContent(modifier)
        }

        OrderStatusScreenState.APPROVED -> {
            OrderApprovedScreenContent(modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderStatusScreenPreview() {
    OrderStatusScreen(
        uiState = OrderStatusScreenUiState()
    )
}