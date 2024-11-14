package com.nifa.fuel_buddy.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.nifa.fuel_buddy.core.navigation.SetupMainNavGraph
import com.nifa.fuel_buddy.core.ui.theme.FuelBuddyMobileTheme
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.AuthNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FuelBuddyMobileTheme {

                val navHostController = rememberNavController()

                SetupMainNavGraph(
                    navHostController = navHostController,
                    startDestination = AuthNavigation.AuthNavGraph
                )
            }
        }
    }
}
