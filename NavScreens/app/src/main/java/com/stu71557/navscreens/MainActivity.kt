package com.stu71557.navscreens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stu71557.navscreens.ui.theme.NavScreensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavScreensTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = "landing_page"
    ) {
        composable("landing_page") { LandingPage(navController) }
        composable("screen2/{word}") { backStackEntry ->
            Screen2(navController, backStackEntry.arguments?.getString("word") ?: "")
        }
        composable("screen3/{word}") { backStackEntry ->
            Screen3(navController, backStackEntry.arguments?.getString("word") ?: "")
        }
    }
}

@Composable
fun LandingPage(navController: NavController) {
    var word by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = word,
            onValueChange = { word = it },
            label = { Text("Type your word") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (word.isNotEmpty()) {
                    navController.navigate("screen2/$word")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Screen 2")
        }
    }
}

@Composable
fun Screen2(navController: NavController, word: String) {
    val upperWord = word.uppercase()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = upperWord, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("landing_page") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Landing Page")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("screen3/$word") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Screen 3")
        }
    }
}

@Composable
fun Screen3(navController: NavController, word: String) {

    val vowelCount = word.count { it.lowercaseChar() in "aeiou" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Number of vowels: $vowelCount", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("landing_page") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Landing Page")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("screen2/$word") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Screen 2")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NavScreensTheme {
        AppNavigation()
    }
}
