package com.stu.joborganize.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.stu.joborganize.ui.screens.Decorators
import com.stu.joborganize.ui.screens.Home
import com.stu.joborganize.ui.screens.ReservedDecorators
import com.stu.joborganize.ui.screens.contracted_decorators.ContractedDecoratorsScreen
import com.stu.joborganize.ui.screens.decorators.DecoratorsScreen
import com.stu.joborganize.ui.screens.home.HomeScreen

@Composable
fun Navigation(
    navController: NavHostController,
    showDialogAddNewJob: Boolean,
    onDialogAddNewJobDismiss: () -> Unit,
    modifier: Modifier = Modifier) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                showDialogAddNewJob = showDialogAddNewJob,
                onDialogAddNewJobDismiss = onDialogAddNewJobDismiss,
                onNavigateToDecorators = { jobId ->
                    navController.navigate(Decorators(jobId))
                }
            )
        }

        composable<Decorators> { backStackEntry ->
            val decorators = backStackEntry.toRoute<Decorators>()
            DecoratorsScreen(
                jobId = decorators.jobId
            )
        }

        composable<ReservedDecorators> {
           ContractedDecoratorsScreen()
        }
    }
}