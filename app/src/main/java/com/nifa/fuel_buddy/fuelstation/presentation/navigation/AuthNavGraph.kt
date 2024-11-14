package com.nifa.fuel_buddy.fuelstation.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nifa.fuel_buddy.fuelstation.presentation.SignInScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<AuthNavigation.AuthNavGraph>(
        startDestination = AuthNavigation.SignInScreen
    ) {

        composable<AuthNavigation.SignInScreen> {
            SignInScreen()
        }

        composable<AuthNavigation.ChooseSignUpScreen> {

        }

        composable<AuthNavigation.UserSignUpScreen> {

        }

        composable<AuthNavigation.FuelStationSignUpScreen> {

        }

        composable<AuthNavigation.DLVerificationScreen> {

        }

        composable<AuthNavigation.ForgotPasswordScreen> {

        }
    }
}