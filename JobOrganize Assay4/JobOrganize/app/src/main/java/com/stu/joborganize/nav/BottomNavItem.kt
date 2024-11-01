package com.stu.joborganize.nav

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import com.stu.joborganize.ui.screens.Decorators
import com.stu.joborganize.ui.screens.Home
import com.stu.joborganize.ui.screens.ReservedDecorators


sealed class BottomNavItem (val title: String, val icon: ImageVector, val route: Any) {

    abstract fun toRoute(navBackStackEntry: NavBackStackEntry): Boolean

    data object HomeItem : BottomNavItem("Home", Icons.Default.Home, Home){
        override fun toRoute(navBackStackEntry: NavBackStackEntry): Boolean {
            return try {
                val home = navBackStackEntry.toRoute<Home>()
                Log.d("BottomNavItem", "toRoute: $home")
                true
            } catch (e: Exception) {
                Log.d("BottomNavItem", "toRoute: ${e.message}")
                false
            }
        }
    }

    data object DecoratorsItem : BottomNavItem("Decorators", Icons.Default.Person, Decorators){
        override fun toRoute(navBackStackEntry: NavBackStackEntry): Boolean {
            return try {
                val decorators = navBackStackEntry.toRoute<ReservedDecorators>()
                Log.d("BottomNavItem", "toRoute: $decorators")
                true
            } catch (e: Exception) {
                Log.d("BottomNavItem", "toRoute: ${e.message}")
                false
            }

        }

    }
}