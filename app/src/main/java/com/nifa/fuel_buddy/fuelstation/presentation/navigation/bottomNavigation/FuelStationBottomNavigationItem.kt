package com.nifa.fuel_buddy.fuelstation.presentation.navigation.bottomNavigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.presentation.navigation.NavigationScreen
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.FuelStationNavigation

enum class FuelStationBottomNavigationItem(
    @DrawableRes val icon: Int,
    val screen: NavigationScreen,
    @StringRes val label: Int
) {
    LIVE_ORDERS(
        icon = R.drawable.ic_live_order,
        screen = FuelStationNavigation.LiveOrderScreen,
        label = R.string.live_order
    ),
    HISTORY(
        icon = R.drawable.ic_work_history,
        screen = FuelStationNavigation.HistoryScreen,
        label = R.string.history
    ),
    ACCOUNT(
        icon = R.drawable.ic_account,
        screen = FuelStationNavigation.AccountScreen,
        label = R.string.account
    )
}
