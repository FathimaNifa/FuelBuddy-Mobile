package com.nifa.fuel_buddy.auth.presentation.navigation

import com.nifa.fuel_buddy.auth.presentation.feature.accounttype.AccountType
import com.nifa.fuel_buddy.core.navigation.NavigationScreen
import kotlinx.serialization.Serializable

sealed interface AuthNavigation : NavigationScreen {

    @Serializable
    data object AuthNavGraph : AuthNavigation

    @Serializable
    data class SignInScreen(val accountType: AccountType) : AuthNavigation

    @Serializable
    data object ChooseAccountTypeScreen : AuthNavigation

    @Serializable
    data object UserSignUpScreen : AuthNavigation

    @Serializable
    data object FuelStationSignUpScreen : AuthNavigation

    @Serializable
    data object DLVerificationScreen : AuthNavigation

    @Serializable
    data object ForgotPasswordScreen : AuthNavigation

    @Serializable
    data object LoaderScreen : AuthNavigation
}