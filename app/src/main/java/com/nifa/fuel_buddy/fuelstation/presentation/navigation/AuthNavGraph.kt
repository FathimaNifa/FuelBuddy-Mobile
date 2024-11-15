package com.nifa.fuel_buddy.fuelstation.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nifa.fuel_buddy.core.utils.ext.navigateAndPopupAllBackStack
import com.nifa.fuel_buddy.core.utils.ext.navigateTo
import com.nifa.fuel_buddy.fuelstation.presentation.DLVerificationScreen
import com.nifa.fuel_buddy.fuelstation.presentation.ForgotPasswordScreen
import com.nifa.fuel_buddy.fuelstation.presentation.FuelStationSignUpScreen
import com.nifa.fuel_buddy.fuelstation.presentation.UserSignUpScreen
import com.nifa.fuel_buddy.fuelstation.presentation.signin.SignInScreen
import com.nifa.fuel_buddy.fuelstation.presentation.signin.SignInScreenViewModel
import com.nifa.fuel_buddy.fuelstation.presentation.signup.choosesingup.ChooseSignUpScreen
import com.nifa.fuel_buddy.fuelstation.presentation.signup.choosesingup.ChooseSignUpViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation<AuthNavigation.AuthNavGraph>(
        startDestination = AuthNavigation.SignInScreen
    ) {

        composable<AuthNavigation.SignInScreen> {
            val viewModel = viewModel<SignInScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            SignInScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navController::navigateTo,
                navigateAndPopupBackStack = navController::navigateAndPopupAllBackStack
            )
        }

        composable<AuthNavigation.ChooseSignUpScreen> {
            val viewModel = viewModel<ChooseSignUpViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ChooseSignUpScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navController::navigateTo
            )
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