package com.nifa.fuel_buddy.core.utils.ext

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen

fun NavHostController.navigateTo(navigationScreen: NavigationScreen) {
    navigate(navigationScreen) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.navigateAndPopupAllBackStack(navigationScreen: NavigationScreen) {
    navigate(navigationScreen) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

fun NavHostController.navigateAndPopupTo(navigationScreen: NavigationScreen) {
    navigate(navigationScreen) {
        popUpTo(navigationScreen) {
            inclusive = true
        }
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