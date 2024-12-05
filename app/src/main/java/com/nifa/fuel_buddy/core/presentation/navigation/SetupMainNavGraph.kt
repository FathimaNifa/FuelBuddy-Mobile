package com.nifa.fuel_buddy.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nifa.fuel_buddy.auth.presentation.navigation.authNavGraph
import com.nifa.fuel_buddy.fuelstation.presentation.feature.main.FuelStationMainScreen
import com.nifa.fuel_buddy.fuelstation.presentation.feature.main.FuelStationMainViewModel
import com.nifa.fuel_buddy.user.presentation.feature.main.UserNavMainScreen
import com.nifa.fuel_buddy.user.presentation.feature.main.UserNavMainScreenViewModel

@Composable
fun SetupMainNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: NavigationScreen
) {

    NavHost(
        navController = navHostController,
        modifier = modifier,
        startDestination = startDestination
    ) {

        authNavGraph(navHostController)

        composable<NavGraphs.FuelStationNavGraph> {
            val viewModel = viewModel<FuelStationMainViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            FuelStationMainScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction
            )
        }


        composable<NavGraphs.UserNavGraph> {
            val viewModel = viewModel<UserNavMainScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            UserNavMainScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction
            )
        }
    }
}
