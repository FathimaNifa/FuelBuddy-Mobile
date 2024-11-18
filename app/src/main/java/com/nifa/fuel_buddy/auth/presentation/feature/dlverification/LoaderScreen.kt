package com.nifa.fuel_buddy.auth.presentation.feature.dlverification

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nifa.fuel_buddy.core.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun LoaderScreen(
    modifier: Modifier = Modifier,
    uiState: LoaderScreenUiState,
    uiEvent: Flow<LoaderScreenUiEvent>,
    navigateAndClearBackStack: (NavigationScreen) -> Unit
) {

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is LoaderScreenUiEvent.NavigateAndClearBackStack -> {
                navigateAndClearBackStack.invoke(event.navigationScreen)
            }
        }
    }

    when (uiState.screenState) {
        LoaderScreenState.LOADING -> LoadingContent(modifier)
        LoaderScreenState.VERIFIED -> SuccessContent(modifier)
    }
}

@Preview
@Composable
private fun LoaderScreenPreview() {

    LoaderScreen(
        uiEvent = emptyFlow(),
        uiState = LoaderScreenUiState(),
        navigateAndClearBackStack = {}
    )
}