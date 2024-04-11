package com.stu71557.a71557_lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.stu71557.a71557_lab2.ui.theme._71557_Lab2Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            
            val navController = rememberNavController()
            
            _71557_Lab2Theme {
                Scaffold(topBar = {
                    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ), title = {
                        Image(
                            modifier = Modifier
                                .padding(82.dp)
                                .fillMaxSize(),
                            painter = painterResource(id = R.drawable.logo_cineworld),
                            contentDescription = null
                        )
                    }
                    )
                }) { paddingValues ->
                    AppNavigation(
                        paddingValues = paddingValues, navController = navController
                    )
                }
            }
        }
    }
}
    


