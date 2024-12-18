package com.nifa.fuel_buddy.core.presentation.userFeedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.auth.presentation.feature.composable.AuthCTA
import com.nifa.fuel_buddy.core.presentation.userFeedback.components.FeedbackTextField
import com.nifa.fuel_buddy.core.utils.Font
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun FeedBackScreen(
    modifier: Modifier = Modifier,
    uiState: FeedbackUiState,
    uiAction: (FeedbackUiAction) -> Unit,
    uiEvent: Flow<FeedbackUiEvent>
) {

    Column(
        modifier = modifier.fillMaxSize()
            .background(colorResource(R.color.black)),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Box(
            modifier = Modifier.weight(0.1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.feed_back),
                color = Color.White,
                fontSize = 22.sp,
                lineHeight = 24.sp,
                fontFamily = Font.JosefinBold,
                modifier = modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()

            )
        }

        FeedbackTextField(
            modifier = Modifier
                .weight(1f),
            maxTextCount = uiState.maxTextCount,
            currentTextCount = uiState.currentTextCount,
            text = uiState.text,
            onTextChange = { uiAction.invoke(FeedbackUiAction.OnTextChange(it)) }
        )
        Box(
            modifier = Modifier.weight(0.2f),
            contentAlignment = Alignment.Center
        ) {
            AuthCTA(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = stringResource(R.string.submit),
                isLoading = uiState.isLoading,
                enabled = uiState.enableSubmitCTA,
                onClick = { uiAction.invoke(FeedbackUiAction.OnSubmitButtonClicked) }
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
        uiEvent = emptyFlow()
    )
}