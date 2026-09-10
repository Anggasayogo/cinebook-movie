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

@Composable
fun SetupNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController,startDestination = Screen.Home.route,modifier = modifier) {
        // 1. Home Screen
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

        // 2. Detail Screen (Menerima parameter ID)
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

        // 3. Search Screen
        composable(route = Screen.Search.route) {
            SearchScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 4. Favorite Screen
        composable(route = Screen.Favorite.route) {
            FavoriteScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId))
                }
            )
        }
    }
}