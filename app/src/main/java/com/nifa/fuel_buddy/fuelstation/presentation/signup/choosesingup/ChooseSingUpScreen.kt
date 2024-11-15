package com.nifa.fuel_buddy.fuelstation.presentation.signup.choosesingup

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
import com.nifa.fuel_buddy.core.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import com.nifa.fuel_buddy.fuelstation.presentation.composable.AuthCTA
import com.nifa.fuel_buddy.fuelstation.presentation.composable.AuthRadioButton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ChooseSignUpScreen(
    modifier: Modifier = Modifier,
    uiState: ChooseSignUpScreenUiState,
    uiAction: (ChooseSignUpScreenUiAction) -> Unit,
    uiEvent: Flow<ChooseSignUpScreenUiEvent>,
    navigateToCallback: (NavigationScreen) -> Unit
) {

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is ChooseSignUpScreenUiEvent.NavigateTo -> navigateToCallback.invoke(event.navigationScreen)
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
                text = stringResource(R.string.choose_sign_up).uppercase(),
                color = colorResource(R.color.white),
                fontFamily = Font.JosefinBold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )

            AuthRadioButton(
                isSelected = uiState.selectedAccountType == AccountType.USER,
                icon = R.drawable.ic_user_icon_svg,
                text = stringResource(R.string.user),
                onClick = {
                    uiAction.invoke(
                        ChooseSignUpScreenUiAction.OnAccountTypeSelected(
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
                        ChooseSignUpScreenUiAction.OnAccountTypeSelected(
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
                onClick = { uiAction.invoke(ChooseSignUpScreenUiAction.OnContinueCTAButtonClicked) }
            )
        }
    }

}

@Preview
@Composable
private fun ChooseSignUpScreenPreview() {
    ChooseSignUpScreen(
        uiState = ChooseSignUpScreenUiState(),
        uiAction = {},
        uiEvent = emptyFlow(),
        navigateToCallback = {}
    )
}