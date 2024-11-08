package com.nifa.fuel_buddy.presentation.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nifa.fuel_buddy.presentation.core.navigation.NavigationScreen
import com.nifa.fuel_buddy.presentation.core.navigation.SetupNavGraph
import com.nifa.fuel_buddy.presentation.core.navigation.bottomnavigation.SetupBottomNavigation
import com.nifa.fuel_buddy.presentation.utils.ext.navigateBottomBar
import com.nifa.fuel_buddy.ui.theme.FuelBuddyMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FuelBuddyMobileTheme {

                val navHostController = rememberNavController()
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val viewModel by viewModels<MainViewModel>()
                val uiAction = viewModel::onUiAction
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                navHostController.addOnDestinationChangedListener { _: NavController, destination: NavDestination, _: Bundle? ->
                    destination.route?.let { route ->
                        uiAction.invoke(MainUiAction.OnNavDestinationChanged(route))
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
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

                    SetupNavGraph(
                        navHostController = navHostController,
                        startDestination = NavigationScreen.HomeScreen,
                        modifier = Modifier.padding(bottom = bottomPadding, top = topPadding)
                    )
                }
            }
        }
    }
}
