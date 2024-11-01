package com.stu.joborganize.nav

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.stu.joborganize.ui.screens.Home
import com.stu.joborganize.ui.screens.ReservedDecorators

@Composable
fun BottomNavigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isHome = !currentRoute.toString().contains("Reserved")

    BottomAppBar(
        modifier = modifier
    ) {

        NavigationBarItem(
            selected = isHome,
            onClick = {
                if (!isHome) {
                    navHostController.navigate(Home)
                }
            },
            icon = {
                Icon(
                    imageVector = BottomNavItem.HomeItem.icon,
                    contentDescription = BottomNavItem.HomeItem.title
                )
            }
        )
        NavigationBarItem(
            selected = !isHome,
            onClick = {
                if (isHome) {
                    navHostController.navigate(ReservedDecorators)
                }
            },
            icon = {
                Icon(
                    imageVector = BottomNavItem.DecoratorsItem.icon,
                    contentDescription = BottomNavItem.DecoratorsItem.title
                )
            }
        )
    }
}

