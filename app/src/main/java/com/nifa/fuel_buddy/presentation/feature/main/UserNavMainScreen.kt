package com.nifa.fuel_buddy.presentation.feature.main

import android.os.Bundle
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nifa.fuel_buddy.presentation.core.navigation.UserNavigation
import com.nifa.fuel_buddy.presentation.core.navigation.bottomnavigation.SetupBottomNavigation
import com.nifa.fuel_buddy.presentation.feature.navigation.SetupUserNavGraph
import com.nifa.fuel_buddy.presentation.utils.ext.navigateBottomBar

@Composable
fun UserNavMainScreen(
    uiState: UserNavMainScreenViewModelUiState,
    uiAction: (UserNavMainScreenViewModelUiAction) -> Unit,
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    navHostController.addOnDestinationChangedListener { _: NavController, destination: NavDestination, _: Bundle? ->
        destination.route?.let { route ->
            uiAction.invoke(UserNavMainScreenViewModelUiAction.OnNavDestinationChanged(route))
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            SetupBottomNavigation(
                shouldShowBottomBar = uiState.shouldShowBottomBar,
                currentDestination = navBackStackEntry?.destination?.route,
                onClickedBottomNavigationItem = { navHostController.navigateBottomBar(it.screen) }
            )
        }
    ) { innerPadding ->

        val bottomPadding by animateDpAsState(
            targetValue = if (uiState.shouldShowBottomBar) innerPadding.calculateBottomPadding() else 0.dp,
            label = "bottom-padding"
        )
        val topPadding = innerPadding.calculateTopPadding()

        SetupUserNavGraph(
            navHostController = navHostController,
            startDestination = UserNavigation.HomeScreen,
            modifier = Modifier.padding(bottom = bottomPadding, top = topPadding)
        )
    }

}

@Preview
@Composable
private fun UserNavMainScreenPreview() {
    UserNavMainScreen(
        uiState = UserNavMainScreenViewModelUiState(),
        uiAction = {}
    )
}