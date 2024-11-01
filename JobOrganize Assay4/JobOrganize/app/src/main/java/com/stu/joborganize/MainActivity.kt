package com.stu.joborganize

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stu.joborganize.nav.BottomNavigation
import com.stu.joborganize.nav.Navigation
import com.stu.joborganize.ui.theme.JobOrganizeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            var showDialogAddNewJob by remember { mutableStateOf(false) }


            JobOrganizeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Job Organize") }
                        )
                    },
                    bottomBar = {
                        BottomNavigation(
                            navHostController = navController
                        )
                    },
                    floatingActionButton = {
                        val route = navBackStackEntry?.destination?.route
                        Log.d("MainActivity", "onCreate: route=$route")
                        if (route?.contains("Home") == true) {
                            ExtendedFloatingActionButton(
                                text = { Text(text = "Add new job") },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add"
                                    )
                                },
                                onClick = { showDialogAddNewJob = true })

                        }
                    }) { innerPadding ->

                    Navigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        showDialogAddNewJob = showDialogAddNewJob,
                        onDialogAddNewJobDismiss = { showDialogAddNewJob = false }
                    )
                }
            }
        }
    }
}
