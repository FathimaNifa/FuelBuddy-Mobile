package com.nifa.fuel_buddy.auth.presentation.feature.accounttype

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.auth.presentation.feature.composable.AuthCTA
import com.nifa.fuel_buddy.auth.presentation.feature.composable.AuthRadioButton
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ChooseAccountTypeScreen(
    modifier: Modifier = Modifier,
    uiState: ChooseAccountTypeScreenUiState,
    uiAction: (ChooseAccountTypeScreenUiAction) -> Unit,
    uiEvent: Flow<ChooseAccountTypeScreenUiEvent>,
    navigateToCallback: (NavigationScreen) -> Unit
) {

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is ChooseAccountTypeScreenUiEvent.NavigateTo -> navigateToCallback.invoke(event.navigationScreen)
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
                text = stringResource(R.string.choose_account_type).uppercase(),
                color = colorResource(R.color.white),
                fontFamily = Font.JosefinBold,
                fontSize = 26.sp,
                textAlign = TextAlign.Start
            )

            AuthRadioButton(
                isSelected = uiState.selectedAccountType == AccountType.USER,
                icon = R.drawable.ic_user_icon_svg,
                text = stringResource(R.string.user),
                onClick = {
                    uiAction.invoke(
                        ChooseAccountTypeScreenUiAction.OnAccountTypeSelected(
                            AccountType.USER
                        )
                    )
                }
            )

            AuthRadioButton(
                isSelected = uiState.selectedAccountType == AccountType.FUEL_STATION,
                icon = R.drawable.ic_fuel_station,
                text = stringResource(R.string.fuel_station),
                onClick = {
                    uiAction.invoke(
                        ChooseAccountTypeScreenUiAction.OnAccountTypeSelected(
                            AccountType.FUEL_STATION
                        )
                    )
                }
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom
        ) {

            AuthCTA(
                enabled = uiState.enableContinueCTA,
                text = stringResource(R.string.continue_cta),
                onClick = { uiAction.invoke(ChooseAccountTypeScreenUiAction.OnContinueCTAButtonClicked) }
            )
        }
    }

}

@Preview
@Composable
private fun ChooseSignUpScreenPreview() {
    ChooseAccountTypeScreen(
        uiState = ChooseAccountTypeScreenUiState(),
        uiAction = {},
        uiEvent = emptyFlow(),
        navigateToCallback = {}
    )
}