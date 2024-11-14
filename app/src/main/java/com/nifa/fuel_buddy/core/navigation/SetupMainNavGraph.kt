package com.nifa.fuel_buddy.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.authNavGraph
import com.nifa.fuel_buddy.user.presentation.feature.main.UserNavMainScreen
import com.nifa.fuel_buddy.user.presentation.feature.main.UserNavMainScreenViewModel
import com.nifa.fuel_buddy.user.presentation.navigation.UserNavigation

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

        composable<UserNavigation.UserNavGraph> {
            val viewModel = viewModel<UserNavMainScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            UserNavMainScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction
            )
        }
    }
}
