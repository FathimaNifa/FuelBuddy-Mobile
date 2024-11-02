package com.nifa.fuel_buddy.presentation.navigation.bottomnavigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nifa.fuel_buddy.R

@Composable
fun SetupBottomNavigation(
    modifier: Modifier = Modifier,
    currentDestination: String?,
    onClickedBottomNavigationItem: (BottomNavigationItem) -> Unit
) {

    NavigationBar(
        modifier = modifier,
        containerColor = colorResource(R.color.jet)
    ) {
        BottomNavigationItem.entries.forEach { bottomNavigationItems ->

            val isSelected =
                currentDestination == bottomNavigationItems.screen::class.qualifiedName

            NavigationBarItem(
                selected = isSelected,
                onClick = { onClickedBottomNavigationItem.invoke(bottomNavigationItems) },
                icon = {
                    Icon(
                        painterResource(
                            id = bottomNavigationItems.icon
                        ),
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(bottomNavigationItems.label)
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = colorResource(R.color.black),
                    unselectedIconColor = colorResource(R.color.white),
                    selectedTextColor = colorResource(R.color.saffron),
                    unselectedTextColor = colorResource(R.color.white),
                    selectedIndicatorColor = colorResource(R.color.saffron),
                    disabledIconColor = colorResource(R.color.gray),
                    disabledTextColor = colorResource(R.color.gray)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SetupBottomNavigationPreview() {
    SetupBottomNavigation(
        currentDestination = BottomNavigationItem.HOME.screen::class.qualifiedName,
        onClickedBottomNavigationItem = {}
    )
}
