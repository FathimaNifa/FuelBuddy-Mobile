package com.nifa.fuel_buddy.auth.presentation.feature.signup.fuelstation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.auth.presentation.feature.composable.AuthCTA
import com.nifa.fuel_buddy.auth.presentation.feature.composable.AuthTextField
import com.nifa.fuel_buddy.core.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun FuelStationSignUpScreen(
    modifier: Modifier = Modifier,
    uiState: FuelStationSignUpScreenUiState,
    uiAction: (FuelStationSignUpScreenUiAction) -> Unit,
    uiEvent: Flow<FuelStationSignUpScreenUiEvent>,
    navigateAndPopupBackStack: (NavigationScreen) -> Unit
) {

    val context = LocalContext.current

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is FuelStationSignUpScreenUiEvent.NavigateAndPopupBackStack -> {
                navigateAndPopupBackStack.invoke(event.navigationScreen)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(color = colorResource(R.color.black))
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceAround

    ) {

        Text(
            text = stringResource(R.string.sign_up).uppercase(),
            color = colorResource(R.color.white),
            fontFamily = Font.JosefinBold,
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )

        AuthTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            isError = uiState.showUserNameAsError,
            label = stringResource(R.string.user_name),
            leadingIconResId = R.drawable.ic_account,
            onValueChange = { uiAction.invoke(FuelStationSignUpScreenUiAction.TypingUserName(it)) },
            value = uiState.typedUserName,
            supportingText = uiState.userNameSupportingText?.asString(context)
        )

        AuthTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            label = stringResource(R.string.email_id),
            isError = uiState.showEmailIdAsError,
            leadingIconResId = R.drawable.ic_mail,
            onValueChange = { uiAction.invoke(FuelStationSignUpScreenUiAction.TypingEmail(it)) },
            value = uiState.typedEmailId,
            supportingText = uiState.emailIdSupportingText?.asString(context)
        )

        AuthTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            label = stringResource(R.string.registration_number),
            isError = uiState.showRegistrationNumberAsError,
            leadingIconResId = R.drawable.ic_check,
            onValueChange = { uiAction.invoke(FuelStationSignUpScreenUiAction.TypingRegisterNumber(it)) },
            value = uiState.typedRegisterNumber,
            supportingText = uiState.registerNumberSupportingUiText?.asString(context)
        )

        AuthTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            maskText = uiState.maskPassword,
            isError = uiState.showPasswordAsError,
            label = stringResource(R.string.create_new_password),
            leadingIconResId = R.drawable.ic_lock,
            trailingIconResId = if (uiState.maskPassword) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off,
            onTrailingIconClick = { uiAction.invoke(FuelStationSignUpScreenUiAction.OnPasswordVisibilityButtonClicked) },
            onValueChange = { uiAction.invoke(FuelStationSignUpScreenUiAction.TypingPassword(it)) },
            value = uiState.typedPassword,
            supportingText = uiState.passwordSupportingText?.asString(context)
        )

        AuthTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            maskText = uiState.maskConfirmPassword,
            isError = uiState.showConfirmPasswordAsError,
            label = stringResource(R.string.confirm_password),
            leadingIconResId = R.drawable.ic_lock,
            onValueChange = {
                uiAction.invoke(
                    FuelStationSignUpScreenUiAction.TypingConfirmPassword(
                        it
                    )
                )
            },
            value = uiState.typedConfirmPassword,
            supportingText = uiState.confirmPasswordSupportingText?.asString(context),
            trailingIconResId = if (uiState.maskConfirmPassword) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off,
            onTrailingIconClick = { uiAction.invoke(FuelStationSignUpScreenUiAction.OnConfirmPasswordVisibilityButtonClicked) },
        )


        AuthCTA(
            text = stringResource(R.string.sign_up),
            onClick = { uiAction.invoke(FuelStationSignUpScreenUiAction.OnSignUpButtonClicked) }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { uiAction.invoke(FuelStationSignUpScreenUiAction.OnSignInButtonClicked) },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.i_m_a_already_member),
                fontSize = 14.sp,
                fontFamily = Font.JosefinRegular,
                color = colorResource(R.color.white),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.sign_in).uppercase(),
                fontSize = 14.sp,
                fontFamily = Font.JosefinRegular,
                color = colorResource(R.color.saffron),
                textAlign = TextAlign.Center,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
}

@Preview
@Composable
private fun FuelStationSignUpScreenPreview() {
    FuelStationSignUpScreen(
        uiState = FuelStationSignUpScreenUiState(),
        uiAction = {},
        uiEvent = emptyFlow(),
        navigateAndPopupBackStack = {}
    )
}