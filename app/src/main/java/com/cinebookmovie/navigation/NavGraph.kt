package com.cinebookmovie.navigation

import androidx.navigation.compose.NavHost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cinebookmovie.presentation.detail.MovieDetailScreen
import com.cinebookmovie.presentation.favorite.FavoriteScreen
import com.cinebookmovie.presentation.home.HomeScreen
import com.cinebookmovie.presentation.search.SearchScreen
import com.cinebookmovie.presentation.splash.SplashScreen

@Composable
fun SetupNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController,startDestination = Screen.Splash.route,modifier = modifier) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNextMove = { ->
                    navController.navigate(Screen.Home.route){
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                },
                onFavoriteClick = {
                    navController.navigate(Screen.Favorite.route)
                }
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailScreen(
                movieId = movieId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Screen.Search.route) {
            SearchScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Screen.Favorite.route) {
            FavoriteScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                }
            )
        }
    }
}