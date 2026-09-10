package com.cinebookmovie.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object Favorite : Screen("favorite")
    data object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int): String {
            return "detail/$movieId"
        }
    }
}