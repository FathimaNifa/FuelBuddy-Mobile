package com.nifa.fuel_buddy.presentation.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.nifa.fuel_buddy.presentation.core.navigation.SetupMainNavGraph
import com.nifa.fuel_buddy.presentation.core.navigation.UserNavigation
import com.nifa.fuel_buddy.ui.theme.FuelBuddyMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FuelBuddyMobileTheme {

                val navHostController = rememberNavController()

                SetupMainNavGraph(
                    navHostController = navHostController,
                    startDestination = UserNavigation.UserNavGraph
                )
            }
        }
    }
}
