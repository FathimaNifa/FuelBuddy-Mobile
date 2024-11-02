package com.nifa.fuel_buddy.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nifa.fuel_buddy.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.presentation.navigation.SetupNavGraph
import com.nifa.fuel_buddy.presentation.navigation.bottomnavigation.SetupBottomNavigation
import com.nifa.fuel_buddy.presentation.utils.navigateBottomBar
import com.nifa.fuel_buddy.ui.theme.FuelBuddyMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FuelBuddyMobileTheme {

                val navHostController = rememberNavController()
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        SetupBottomNavigation(
                            currentDestination = navBackStackEntry?.destination?.route,
                            onClickedBottomNavigationItem = { navHostController.navigateBottomBar(it.screen) }
                        )
                    }
                ) { innerPadding ->

                    val bottomPadding = innerPadding.calculateBottomPadding()

                    SetupNavGraph(
                        navHostController = navHostController,
                        startDestination = NavigationScreen.HomeScreen,
                        modifier = Modifier.padding(bottom = bottomPadding)
                    )
                }
            }
        }
    }
}
