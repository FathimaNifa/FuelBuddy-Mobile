package com.nifa.fuel_buddy.auth.presentation.dlverification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.auth.presentation.composable.AuthCTA
import com.nifa.fuel_buddy.auth.presentation.composable.AuthTextField
import com.nifa.fuel_buddy.core.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun DLVerificationScreen(
    modifier: Modifier = Modifier,
    uiState: DLVerificationScreenUiState,
    uiAction: (DLVerificationScreenUiAction) -> Unit,
    uiEvent: Flow<DLVerificationScreenUiEvent>,
    navigateToCallback: (NavigationScreen) -> Unit
) {

    val context = LocalContext.current

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is DLVerificationScreenUiEvent.NavigateTo -> {
                navigateToCallback.invoke(event.navigationScreen)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(color = colorResource(R.color.black))
            .padding(30.dp),

        ) {

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = stringResource(R.string.dl_verification).uppercase(),
                color = colorResource(R.color.white),
                fontFamily = Font.JosefinBold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )

            AuthTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                isError = uiState.showDLNumberAsError,
                label = stringResource(R.string.driving_license_number),
                leadingIconResId = R.drawable.ic_license,
                onValueChange = { uiAction.invoke(DLVerificationScreenUiAction.TypingDLNumber(it)) },
                value = uiState.typedDLNumber,
                supportingText = uiState.dlNumberSupportingText?.asString(context)
            )

            AuthTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                isError = uiState.showDOBAsError,
                label = stringResource(R.string.date_of_birth),
                leadingIconResId = R.drawable.ic_calendar,
                onValueChange = { uiAction.invoke(DLVerificationScreenUiAction.TypingDOB(it)) },
                value = uiState.typedDOB,
                supportingText = uiState.dobSupportingText?.asString(context),
                onLeadingIconClick = { uiAction.invoke(DLVerificationScreenUiAction.OnCalendarIconClicked) }
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom
        ) {

            AuthCTA(
                text = stringResource(R.string.verify),
                onClick = { uiAction.invoke(DLVerificationScreenUiAction.OnVerifyButtonClicked) }
            )
        }
    }
}

@Preview
@Composable
private fun DLVerificationScreenPreview() {
    DLVerificationScreen(
        uiState = DLVerificationScreenUiState(),
        uiEvent = emptyFlow(),
        uiAction = {},
        navigateToCallback = {}
    )
}