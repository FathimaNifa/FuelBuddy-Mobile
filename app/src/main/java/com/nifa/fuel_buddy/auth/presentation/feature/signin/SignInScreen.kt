package com.nifa.fuel_buddy.auth.presentation.feature.signin

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import com.nifa.fuel_buddy.core.utils.ext.add
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    uiState: SignInScreenUiState,
    uiAction: (SignInScreenUiAction) -> Unit,
    uiEvent: Flow<SignInScreenUiEvent>,
    navigateToCallback: (NavigationScreen) -> Unit,
    navigateAndPopupBackStack: (NavigationScreen) -> Unit,
) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    uiEvent.CollectAsEffect { event ->
        when (event) {
            is SignInScreenUiEvent.NavigateTo -> navigateToCallback.invoke(event.navigationScreen)
            is SignInScreenUiEvent.NavigateAndPopupBackStack -> navigateAndPopupBackStack.invoke(
                event.navigationScreen
            )

            is SignInScreenUiEvent.ShowErrorSnackBar -> {
                scope.launch {
                    snackbarHostState.showSnackbar(message = event.errorMessage)
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->

        val padding = paddingValues.add(30.dp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .background(color = colorResource(R.color.black))
                .padding(padding),

            ) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.sign_in).uppercase(),
                    color = colorResource(R.color.white),
                    fontFamily = Font.JosefinBold,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center
                )
            }


            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.SpaceAround
            ) {


                AuthTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    isError = uiState.showEmailAsError,
                    label = stringResource(R.string.email_id),
                    leadingIconResId = R.drawable.ic_mail,
                    onValueChange = { uiAction.invoke(SignInScreenUiAction.TypingEmail(it)) },
                    value = uiState.typedEmail,
                    supportingText = uiState.emailSupportingText?.asString(context)
                )

                AuthTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    isError = uiState.showPasswordAsError,
                    maskText = uiState.maskPassword,
                    label = stringResource(R.string.password),
                    leadingIconResId = R.drawable.ic_lock,
                    onValueChange = { uiAction.invoke(SignInScreenUiAction.TypingPassword(it)) },
                    value = uiState.typedPassword,
                    trailingIconResId = if (uiState.maskPassword) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off,
                    onTrailingIconClick = { uiAction.invoke(SignInScreenUiAction.OnPasswordVisibilityButtonClicked) },
                    supportingText = uiState.passwordSupportingText?.asString(context)
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiAction.invoke(SignInScreenUiAction.OnForgotPasswordClicked) },
                    text = stringResource(R.string.forgot_password),
                    fontSize = 14.sp,
                    fontFamily = Font.JosefinRegular,
                    color = colorResource(R.color.saffron),
                    textAlign = TextAlign.Center,
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )

                AuthCTA(
                    isLoading = uiState.isLoading,
                    text = stringResource(R.string.sign_in),
                    onClick = { uiAction.invoke(SignInScreenUiAction.OnSignInButtonClicked) }
                )

                if (uiState.shouldShowSignup) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { uiAction.invoke(SignInScreenUiAction.OnSignUpClicked) },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.i_m_a_new_user),
                            fontSize = 14.sp,
                            fontFamily = Font.JosefinRegular,
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(R.string.sign_up).uppercase(),
                            fontSize = 14.sp,
                            fontFamily = Font.JosefinRegular,
                            color = colorResource(R.color.saffron),
                            textAlign = TextAlign.Center,
                            style = TextStyle(textDecoration = TextDecoration.Underline)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    SignInScreen(
        uiState = SignInScreenUiState(),
        uiAction = {},
        uiEvent = emptyFlow(),
        navigateToCallback = {},
        navigateAndPopupBackStack = {}
    )
}