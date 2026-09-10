package com.cinebookmovie.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponents(
    onFavoriteClick: () -> Unit,
    modifier: Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "CineBook Movie",
                color = Color.White
            )
        },
        actions = {
            IconButton(onClick = { onFavoriteClick() }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite icons",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        )
    )
}