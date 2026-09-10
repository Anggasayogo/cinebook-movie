package com.cinebookmovie.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cinebookmovie.navigation.SetupNavGraph

@Composable
fun CineBookApp() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        SetupNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}