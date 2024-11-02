package com.nifa.fuel_buddy.presentation.navigation.bottomnavigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.presentation.navigation.NavigationScreen

enum class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val screen: NavigationScreen,
    @StringRes val label : Int
) {
    HOME(
        icon = R.drawable.home,
        screen = NavigationScreen.HomeScreen,
        label = R.string.home
    ),
    ACTIVITY(
        icon = R.drawable.activity,
        screen = NavigationScreen.ActivityScreen,
        label = R.string.activity
    ),
    ACCOUNT(
        icon = R.drawable.account,
        screen = NavigationScreen.AccountScreen,
        label = R.string.account
    )
}
