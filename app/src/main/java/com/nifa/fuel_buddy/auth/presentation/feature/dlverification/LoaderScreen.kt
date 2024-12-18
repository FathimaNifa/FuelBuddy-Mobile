package com.nifa.fuel_buddy.auth.presentation.feature.dlverification

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun LoaderScreen(
    modifier: Modifier = Modifier,
    uiState: LoaderScreenUiState,
    uiAction: (LoaderScreenUiAction) -> Unit,
    uiEvent: Flow<LoaderScreenUiEvent>,
    navigateAndPopUpBackStack : (NavigationScreen) -> Unit
) {

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is LoaderScreenUiEvent.NavigateTo -> navigateAndPopUpBackStack.invoke(event.navigationScreen)
        }
    }

    BackHandler {
        uiAction.invoke(LoaderScreenUiAction.OnBackPressed)
    }

    when (uiState.screenState) {
        LoaderScreenState.LOADING -> LoadingContent(modifier)
        LoaderScreenState.VERIFIED -> SuccessContent(modifier)
        LoaderScreenState.ERROR -> FailedContent(
            modifier = modifier,
            errorMessage = uiState.errorMessage,
            onGoBackButtonClicked = { uiAction.invoke(LoaderScreenUiAction.OnGoBackButtonClicked) }
        )
    }
}

@Preview
@Composable
private fun LoaderScreenPreview() {
    LoaderScreen(
        uiState = LoaderScreenUiState(),
        uiAction = {},
        uiEvent = emptyFlow(),
        navigateAndPopUpBackStack = {}
    )
}