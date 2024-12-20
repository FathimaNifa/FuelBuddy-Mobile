package com.nifa.fuel_buddy.core.presentation.feedback

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nifa.fuel_buddy.core.presentation.feedback.components.ErrorScreenContent
import com.nifa.fuel_buddy.core.presentation.feedback.components.FeedbackCollectScreenContent
import com.nifa.fuel_buddy.core.presentation.feedback.components.SuccessScreenContent
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun FeedBackScreen(
    modifier: Modifier = Modifier,
    uiState: FeedbackUiState,
    uiAction: (FeedbackUiAction) -> Unit,
    uiEvent: Flow<FeedbackUiEvent>,
    navigateAndPopupToCallback: (NavigationScreen) -> Unit
) {

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is FeedbackUiEvent.NavigateAndPopup -> navigateAndPopupToCallback.invoke(event.screen)
        }
    }

    when (uiState.feedbackScreenType) {
        FeedbackScreenType.FEEDBACK_COLLECT_SCREEN -> {
            FeedbackCollectScreenContent(
                modifier = modifier,
                uiState = uiState,
                uiAction = uiAction
            )
        }

        FeedbackScreenType.SUCCESS_SCREEN -> {
            SuccessScreenContent(
                backToHomeButtonClicked = { uiAction.invoke(FeedbackUiAction.OnGoBackToHomeButtonClicked) }
            )
        }

        FeedbackScreenType.ERROR_SCREEN -> {
            ErrorScreenContent(
                backToHomeButtonClicked = { uiAction.invoke(FeedbackUiAction.OnGoBackToHomeButtonClicked) }
            )
        }
    }

}

@Preview
@Composable
private fun FeedBackScreenPreview() {
    FeedBackScreen(
        uiState = FeedbackUiState(),
        uiAction = {},
        uiEvent = emptyFlow(),
        navigateAndPopupToCallback = {}
    )
}