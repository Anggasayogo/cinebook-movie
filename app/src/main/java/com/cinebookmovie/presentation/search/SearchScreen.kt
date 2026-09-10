package com.cinebookmovie.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(onBackClick: (Int) -> Unit) {
    // 1. Fixed state declaration
    var query by remember { mutableStateOf("") }

    LazyColumn {
        item {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.LightGray.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
                Spacer(modifier = Modifier.width(12.dp))
                BasicTextField(
                    value = query,
                    onValueChange = { query = it },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black
                    ),
                    modifier = Modifier,
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        onBackClick = {}
    )
}