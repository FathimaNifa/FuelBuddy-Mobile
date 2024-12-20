package com.nifa.fuel_buddy.fuelstation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.nifa.fuel_buddy.core.presentation.feedback.FeedBackScreen
import com.nifa.fuel_buddy.core.presentation.feedback.FeedbackViewModel
import com.nifa.fuel_buddy.core.utils.CustomNavType
import com.nifa.fuel_buddy.core.utils.Deeplink
import com.nifa.fuel_buddy.core.utils.ext.navigateAndPopupTo
import com.nifa.fuel_buddy.core.utils.ext.navigateTo
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderDetails
import com.nifa.fuel_buddy.fuelstation.presentation.feature.account.AccountScreen
import com.nifa.fuel_buddy.fuelstation.presentation.feature.account.AccountScreenViewModel
import com.nifa.fuel_buddy.fuelstation.presentation.feature.history.HistoryDetailScreen
import com.nifa.fuel_buddy.fuelstation.presentation.feature.history.HistoryDetailViewModel
import com.nifa.fuel_buddy.fuelstation.presentation.feature.history.HistoryScreen
import com.nifa.fuel_buddy.fuelstation.presentation.feature.history.HistoryScreenViewModel
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.LiveOrderDetailScreen
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.LiveOrderDetailScreenViewModel
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.LiveOrderScreen
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.LiveOrderScreenViewModel
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.OutForDeliveryScreen
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.OutForDeliveryScreenViewModel
import kotlin.reflect.typeOf

@Composable
fun SetupFuelStationNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: FuelStationNavigation
) {

    NavHost(
        navController = navHostController,
        modifier = modifier,
        startDestination = startDestination
    ) {

        composable<FuelStationNavigation.LiveOrderScreen> {
            val viewModel = hiltViewModel<LiveOrderScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LiveOrderScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navHostController::navigateTo
            )
        }

        composable<FuelStationNavigation.LiveOrderDetailScreen>(
            typeMap = mapOf(
                typeOf<OrderDetails>() to CustomNavType.OrderDetailsType,
            )
        ) {

            val viewModel = hiltViewModel<LiveOrderDetailScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LiveOrderDetailScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navHostController::navigateTo
            )
        }

        composable<FuelStationNavigation.OutForDeliveryScreen>(
            deepLinks = listOf(
                navDeepLink<FuelStationNavigation.OutForDeliveryScreen>(
                    basePath = Deeplink.OUT_FOR_DELIVERY_BASE_PATH,
                )
            )
        ) {
            val viewModel = hiltViewModel<OutForDeliveryScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            OutForDeliveryScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateAndPopupTo = navHostController::navigateAndPopupTo
            )
        }

        composable<FuelStationNavigation.HistoryScreen> {
            val viewModel = hiltViewModel<HistoryScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            HistoryScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navHostController::navigateTo
            )
        }

        composable<FuelStationNavigation.HistoryDetailScreen>(
            typeMap = mapOf(
                typeOf<OrderDetails>() to CustomNavType.OrderDetailsType,
            )
        ) {

            val viewModel = hiltViewModel<HistoryDetailViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            HistoryDetailScreen(
                uiState = uiState
            )
        }

        composable<FuelStationNavigation.AccountScreen> {
            val viewModel = hiltViewModel<AccountScreenViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            AccountScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateToCallback = navHostController::navigateTo
            )
        }

        composable<FuelStationNavigation.FeedbackScreen> {

            val viewModel = hiltViewModel<FeedbackViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            FeedBackScreen(
                uiState = uiState,
                uiAction = viewModel::onUiAction,
                uiEvent = viewModel.uiEvent,
                navigateAndPopupToCallback = navHostController::navigateAndPopupTo
            )
        }
    }
}
