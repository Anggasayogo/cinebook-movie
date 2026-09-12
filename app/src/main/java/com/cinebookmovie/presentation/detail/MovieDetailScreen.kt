package com.cinebookmovie.presentation.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.cinebookmovie.data.local.entity.FavoriteMovieEntity
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    onBackClick: () -> Unit,
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = movieId) {
        viewModel.getMovieDetail(movieId)
    }
    // 2. Observe State dari ViewModel
    val detailMovieState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()

    val lazyListState = rememberLazyListState()
    val headerHeight = 300.dp

    // Menghitung offset scroll hanya saat item pertama terlihat
    val scrollOffset by remember {
        derivedStateOf {
            if (lazyListState.firstVisibleItemIndex == 0) {
                lazyListState.firstVisibleItemScrollOffset
            } else {
                1000 // Offset besar ketika header sudah terlewat
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when(val state = detailMovieState){
            is DetailUiState.Loading -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmer()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.LightGray)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.LightGray)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(12.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.LightGray)
                        )
                    }
                }
            }
            is DetailUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is DetailUiState.Success -> { val movie = state.movie
                AsyncImage(
                    model = movie.poster,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(headerHeight)
                        .graphicsLayer {
                            // Geser gambar dengan kecepatan 50% dari kecepatan scroll
                            translationY = scrollOffset * 0.5f
                            // Efek Fade Out opsional saat di-scroll ke atas
                            alpha = (1f - (scrollOffset / 600f)).coerceIn(0f, 1f)
                        }
                )
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(headerHeight))
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = movie.title.toString(),
                                fontSize = 28.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "GENRE: ${movie.genres.joinToString { it }}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Release Date: ${movie.releaseDate}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = movie.overview.toString(),
                                fontSize = 18.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .statusBarsPadding()
                .padding(10.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.4f),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        IconButton(
            onClick = {
                val currentState = detailMovieState
                Log.d("Hello",currentState.toString())
                if (currentState is DetailUiState.Success) {
                    val movie = currentState.movie
                    Log.d("Hello",movie.toString())
                    val favoriteEntity = FavoriteMovieEntity(
                        id = movie.id,
                        title = movie.title ?: "",
                        posterPath = movie.poster ?: "",
                        overview = movie.overview ?: "",
                        voteAverage = movie.voteAverage ?: 0.0
                    )
                    viewModel.toggleFavorite(favoriteEntity)
                }
            },
            modifier = Modifier
                .statusBarsPadding()
                .padding(10.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.4f),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                tint = if (isFavorite) Color.Red else Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailScreenPreview() {
    MovieDetailScreen(
        onBackClick = {},
        movieId = 0
    )
}
