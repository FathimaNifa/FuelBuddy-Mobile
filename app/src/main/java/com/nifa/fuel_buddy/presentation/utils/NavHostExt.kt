package com.nifa.fuel_buddy.presentation.utils

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.nifa.fuel_buddy.presentation.navigation.NavigationScreen

fun NavHostController.navigateFromSetupScreen(navigationScreen: NavigationScreen) {
    navigate(navigationScreen) {
        popUpTo(currentBackStackEntry?.destination?.route.toString()) {
            inclusive = true
        }
    }
}

fun NavHostController.navigateTo(navigationScreen: NavigationScreen) {
    navigate(navigationScreen) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.navigateBottomBar(navigationScreen: NavigationScreen) {
    if (currentBackStackEntry?.destination?.route != navigationScreen::class.qualifiedName) {
        navigate(navigationScreen) {
            popUpTo(graph.findStartDestination().id) {
                inclusive = true
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}