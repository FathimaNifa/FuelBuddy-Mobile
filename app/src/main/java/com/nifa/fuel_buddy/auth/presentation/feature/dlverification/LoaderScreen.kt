package com.nifa.fuel_buddy.auth.presentation.feature.dlverification

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoaderScreen(
    modifier: Modifier = Modifier,
    uiState: LoaderScreenUiState,
) {

    when (uiState.screenState) {
        LoaderScreenState.LOADING -> LoadingContent(modifier)
        LoaderScreenState.VERIFIED -> SuccessContent(modifier)
    }
}

@Preview
@Composable
private fun LoaderScreenPreview() {
    LoaderScreen(
        uiState = LoaderScreenUiState(),
    )
}