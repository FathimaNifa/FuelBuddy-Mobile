package com.nifa.fuel_buddy.user.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nifa.fuel_buddy.core.utils.CustomNavType
import com.nifa.fuel_buddy.core.utils.ext.navigateTo
import com.nifa.fuel_buddy.user.domain.model.FuelStation
import com.nifa.fuel_buddy.user.domain.model.Product
import com.nifa.fuel_buddy.user.presentation.feature.account.AccountScreen
import com.nifa.fuel_buddy.user.presentation.feature.account.AccountScreenViewModel
import com.nifa.fuel_buddy.user.presentation.feature.activity.ActivityDetailScreen
import com.nifa.fuel_buddy.user.presentation.feature.activity.ActivityDetailScreenViewModel
import com.nifa.fuel_buddy.user.presentation.feature.activity.ActivityScreen
import com.nifa.fuel_buddy.user.presentation.feature.activity.ActivityScreenViewModel
import com.nifa.fuel_buddy.user.presentation.feature.home.HomeDetailScreen
import com.nifa.fuel_buddy.user.presentation.feature.home.HomeDetailScreenViewModel
import com.nifa.fuel_buddy.user.presentation.feature.home.HomeScreen
import com.nifa.fuel_buddy.user.presentation.feature.home.HomeScreenViewModel
import com.nifa.fuel_buddy.user.presentation.feature.home.OrderStatusScreen
import com.nifa.fuel_buddy.user.presentation.feature.home.OrderStatusScreenViewModel
import com.nifa.fuel_buddy.user.presentation.feature.home.OrderTrackingScreen
import com.nifa.fuel_buddy.user.presentation.feature.home.OrderTrackingViewModel
import kotlin.reflect.typeOf

@Composable
fun SetupUserNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: UserNavigation
) {

    NavHost(
        navController = navHostController,
        modifier = modifier,
        startDestination = startDestination
    ) {

        composable<UserNavigation.HomeScreen> {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navHostController::navigateTo
            )
        }

        composable<UserNavigation.HomeDetailScreen>(
            typeMap = mapOf(
                typeOf<FuelStation>() to CustomNavType.FuelStationType,
                typeOf<Product>() to CustomNavType.ProductType
            )
        ) {

            val fuelStation = it.toRoute<UserNavigation.HomeDetailScreen>().fuelStation

            val viewModel = hiltViewModel<HomeDetailScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            viewModel.updateFuelStationUiState(fuelStation)

            HomeDetailScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navHostController::navigateTo
            )
        }

        composable<UserNavigation.OrderStatusScreen> {
            val viewModel = viewModel<OrderStatusScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            OrderStatusScreen(
                uiState = uiState
            )
        }

        composable<UserNavigation.ActivityScreen> {

            val viewModel = hiltViewModel<ActivityScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ActivityScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navHostController::navigateTo
            )
        }

        composable<UserNavigation.ActivityDetailScreen> {
            val viewModel = hiltViewModel<ActivityDetailScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            ActivityDetailScreen(
                uiState = uiState
            )
        }

        composable<UserNavigation.AccountScreen> {

            val viewModel = hiltViewModel<AccountScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            AccountScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction
            )
        }

        composable<UserNavigation.OrderTrackingScreen> {

            val viewModel = hiltViewModel<OrderTrackingViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            OrderTrackingScreen(
                uiState = uiState
            )
        }
    }
}
