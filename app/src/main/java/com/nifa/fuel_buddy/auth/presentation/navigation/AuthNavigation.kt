package com.nifa.fuel_buddy.auth.presentation.navigation

import com.nifa.fuel_buddy.auth.domain.model.request.UserSignUpRequest
import com.nifa.fuel_buddy.auth.presentation.feature.accounttype.AccountType
import com.nifa.fuel_buddy.core.navigation.NavigationScreen
import kotlinx.serialization.Serializable

sealed interface AuthNavigation : NavigationScreen {

    @Serializable
    data object ChooseAccountTypeScreen : AuthNavigation

    @Serializable
    data class SignInScreen(val accountType: AccountType) : AuthNavigation

    @Serializable
    data object UserSignUpScreen : AuthNavigation

    @Serializable
    data class DLVerificationScreen(
        val userName: String,
        val userEmail: String,
        val password: String
    ) : AuthNavigation

    @Serializable
    data class LoaderScreen(val userSingUpRequest: UserSignUpRequest) : AuthNavigation

    @Serializable
    data object FuelStationSignUpScreen : AuthNavigation

    @Serializable
    data object ForgotPasswordScreen : AuthNavigation
}