package com.nifa.fuel_buddy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nifa.fuel_buddy.presentation.account.AccountScreen
import com.nifa.fuel_buddy.presentation.activity.ActivityScreen
import com.nifa.fuel_buddy.presentation.home.HomeScreen
import com.nifa.fuel_buddy.presentation.home.HomeScreenViewModel

@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: NavigationScreen
) {

    NavHost(
        navController = navHostController,
        modifier = modifier,
        startDestination = startDestination
    ) {

        composable<NavigationScreen.HomeScreen> {
            val viewModel = viewModel<HomeScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent
            )
        }

        composable<NavigationScreen.ActivityScreen> {
            ActivityScreen()
        }

        composable<NavigationScreen.AccountScreen> {
            AccountScreen()
        }
    }
}
