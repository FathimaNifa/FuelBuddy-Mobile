package com.nifa.fuel_buddy.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.nifa.fuel_buddy.core.navigation.SetupMainNavGraph
import com.nifa.fuel_buddy.core.ui.theme.FuelBuddyMobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        setContent {
            FuelBuddyMobileTheme {

                val viewModel = hiltViewModel<MainViewModel>()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val navHostController = rememberNavController()

                splashScreen.setKeepOnScreenCondition {
                    uiState.keepSplashScreen
                }

                SetupMainNavGraph(
                    navHostController = navHostController,
                    startDestination = uiState.startDestination
                )
            }
        }
    }
}
