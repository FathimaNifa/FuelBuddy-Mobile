package com.nifa.fuel_buddy.auth.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nifa.fuel_buddy.auth.presentation.feature.ForgotPasswordScreen
import com.nifa.fuel_buddy.auth.presentation.feature.accounttype.ChooseAccountTypeScreen
import com.nifa.fuel_buddy.auth.presentation.feature.accounttype.ChooseAccountTypeViewModel
import com.nifa.fuel_buddy.auth.presentation.feature.dlverification.DLVerificationScreen
import com.nifa.fuel_buddy.auth.presentation.feature.dlverification.DLVerificationScreenViewModel
import com.nifa.fuel_buddy.auth.presentation.feature.dlverification.LoaderScreen
import com.nifa.fuel_buddy.auth.presentation.feature.dlverification.LoaderScreenViewModel
import com.nifa.fuel_buddy.auth.presentation.feature.signin.SignInScreen
import com.nifa.fuel_buddy.auth.presentation.feature.signin.SignInScreenViewModel
import com.nifa.fuel_buddy.auth.presentation.feature.signup.fuelstation.FuelStationSignUpScreen
import com.nifa.fuel_buddy.auth.presentation.feature.signup.fuelstation.FuelStationSignUpScreenViewModel
import com.nifa.fuel_buddy.auth.presentation.feature.signup.user.UserSignUpScreen
import com.nifa.fuel_buddy.auth.presentation.feature.signup.user.UserSignUpScreenViewModel
import com.nifa.fuel_buddy.core.utils.ext.navigateAndPopupAllBackStack
import com.nifa.fuel_buddy.core.utils.ext.navigateTo

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation<AuthNavigation.AuthNavGraph>(
        startDestination = AuthNavigation.ChooseAccountTypeScreen
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

        composable<AuthNavigation.ChooseAccountTypeScreen> {
            val viewModel = viewModel<ChooseAccountTypeViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ChooseAccountTypeScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navController::navigateTo
            )
        }

        composable<AuthNavigation.UserSignUpScreen> {
            val viewModel = viewModel<UserSignUpScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            UserSignUpScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navController::navigateTo,
                navigateAndPopupBackStack = navController::navigateAndPopupAllBackStack
            )
        }

        composable<AuthNavigation.FuelStationSignUpScreen> {
            val viewModel = viewModel<FuelStationSignUpScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            FuelStationSignUpScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateAndPopupBackStack = navController::navigateAndPopupAllBackStack
            )
        }

        composable<AuthNavigation.DLVerificationScreen> {
            val viewModel = viewModel<DLVerificationScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            DLVerificationScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navController::navigateTo
            )
        }

        composable<AuthNavigation.ForgotPasswordScreen> {
            ForgotPasswordScreen()
        }

        composable<AuthNavigation.LoaderScreen> {
            val viewModel = viewModel<LoaderScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LoaderScreen(
                uiEvent = viewModel.uiEvent,
                uiState = uiState,
                navigateAndClearBackStack = navController::navigateAndPopupAllBackStack
            )
        }
    }
}