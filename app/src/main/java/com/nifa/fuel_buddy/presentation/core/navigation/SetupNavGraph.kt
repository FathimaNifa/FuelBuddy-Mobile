package com.nifa.fuel_buddy.presentation.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nifa.fuel_buddy.domain.FuelStation
import com.nifa.fuel_buddy.domain.Product
import com.nifa.fuel_buddy.presentation.feature.account.AccountScreen
import com.nifa.fuel_buddy.presentation.feature.activity.ActivityDetailScreen
import com.nifa.fuel_buddy.presentation.feature.activity.ActivityDetailScreenViewModel
import com.nifa.fuel_buddy.presentation.feature.activity.ActivityScreen
import com.nifa.fuel_buddy.presentation.feature.activity.ActivityScreenViewModel
import com.nifa.fuel_buddy.presentation.feature.home.HomeDetailScreen
import com.nifa.fuel_buddy.presentation.feature.home.HomeDetailScreenViewModel
import com.nifa.fuel_buddy.presentation.feature.home.HomeScreen
import com.nifa.fuel_buddy.presentation.feature.home.HomeScreenViewModel
import com.nifa.fuel_buddy.presentation.feature.home.OrderStatusScreen
import com.nifa.fuel_buddy.presentation.utils.CustomNavType
import com.nifa.fuel_buddy.presentation.utils.ext.navigateTo
import kotlin.reflect.typeOf

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
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navHostController::navigateTo
            )
        }

        composable<NavigationScreen.HomeDetailScreen>(
            typeMap = mapOf(
                typeOf<FuelStation>() to CustomNavType.FuelStationType,
                typeOf<Product>() to CustomNavType.ProductType
            )
        ) {

            val fuelStation = it.toRoute<NavigationScreen.HomeDetailScreen>().fuelStation

            val viewModel = viewModel<HomeDetailScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.updateFuelStationUiState(fuelStation)

            HomeDetailScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navHostController::navigateTo
            )
        }

        composable<NavigationScreen.OrderStatusScreen> {
            OrderStatusScreen()
        }

        composable<NavigationScreen.ActivityScreen> {

            val viewModel = viewModel<ActivityScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ActivityScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navHostController::navigateTo
            )
        }

        composable<NavigationScreen.ActivityDetailScreen>(
            typeMap = mapOf(
                typeOf<FuelStation>() to CustomNavType.FuelStationType,
                typeOf<Product>() to CustomNavType.ProductType
            )
        ) {
            val fuelStation = it.toRoute<NavigationScreen.HomeDetailScreen>().fuelStation

            val viewModel = viewModel<ActivityDetailScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.updateFuelStationUiState(fuelStation)

            ActivityDetailScreen(
                uiState = uiState
            )
        }

        composable<NavigationScreen.AccountScreen> {
            AccountScreen(Modifier)
        }
    }
}
