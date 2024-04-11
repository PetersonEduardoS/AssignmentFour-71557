package com.stu71557.a71557_lab2

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(), paddingValues: PaddingValues
){
    val viewModel: MovieViewModel = viewModel()
    val movies = viewModel.movies

    NavHost(
        navController = navController,
        startDestination = Routes.FirstScreen.route,
    ){
        composable(route = Routes.FirstScreen.route) {
                Screen1(movies = movies,
                paddingValues = paddingValues,
                onNavigateToSecondScreen = { movie ->
                    val movieId = movie.id
                    navController.navigate("${Routes.SecondScreen.route}/$movieId")
                })
        }
        composable(route = "${Routes.SecondScreen.route}/{movieId}",
            arguments = listOf(navArgument("movieId") {
                type = NavType.IntType
            })) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")!!
            val movie = viewModel.getMovieById(movieId)

            Screen2(
                movie = movie,
                paddingValues = paddingValues
            )
        }
        composable(route = Routes.ThirdScreen.route) {
            Screen3(navController = navController)
        }
    }
}