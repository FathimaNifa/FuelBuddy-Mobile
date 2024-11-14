package com.nifa.fuel_buddy.fuelstation.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nifa.fuel_buddy.fuelstation.presentation.ChooseSignUpScreen
import com.nifa.fuel_buddy.fuelstation.presentation.DLVerificationScreen
import com.nifa.fuel_buddy.fuelstation.presentation.ForgotPasswordScreen
import com.nifa.fuel_buddy.fuelstation.presentation.FuelStationSignUpScreen
import com.nifa.fuel_buddy.fuelstation.presentation.SignInScreen
import com.nifa.fuel_buddy.fuelstation.presentation.UserSignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<AuthNavigation.AuthNavGraph>(
        startDestination = AuthNavigation.DLVerificationScreen
    ) {

        composable<AuthNavigation.SignInScreen> {
            SignInScreen()
        }

        composable<AuthNavigation.ChooseSignUpScreen> {
            ChooseSignUpScreen()
        }

        composable<AuthNavigation.UserSignUpScreen> {
            UserSignUpScreen()
        }

        composable<AuthNavigation.FuelStationSignUpScreen> {
            FuelStationSignUpScreen()
        }

        composable<AuthNavigation.DLVerificationScreen> {
            DLVerificationScreen()
        }

        composable<AuthNavigation.ForgotPasswordScreen> {
            ForgotPasswordScreen()
        }
    }
}