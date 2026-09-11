package com.cinebookmovie.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.cinebookmovie.presentation.components.PopularMoviesBannerSection
import com.cinebookmovie.presentation.components.TopBarComponents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val popularMovies by viewModel.popularMovies.collectAsStateWithLifecycle()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsStateWithLifecycle()
    val topRatedMovies by viewModel.topRatedMovies.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBarComponents(
                onFavoriteClick = {onFavoriteClick()},
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            item {
                when(val state = popularMovies){
                    is PopularMovieUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is PopularMovieUiState.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.message,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    is PopularMovieUiState.Success -> { val movies = state.popularMovie
                        PopularMoviesBannerSection(
                            movies = movies,
                            onMovieClick = onMovieClick,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
            }
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Top Rated",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    when(val topRatedState = topRatedMovies){
                        is TopRatedMovieUiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is TopRatedMovieUiState.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = topRatedState.message,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                        is TopRatedMovieUiState.Success -> {
                            val topRatedMov = topRatedState.topRatedMovie
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ){
                                items(
                                    items = topRatedMov,
                                    key = { it.id }
                                ){ topRatedMovies ->
                                    Column(
                                        modifier = Modifier.width(150.dp)
                                    ){
                                        AsyncImage(
                                            model = topRatedMovies.posterUrl,
                                            contentDescription = topRatedMovies.title,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .width(140.dp)
                                                .height(160.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable(
                                                    onClick = { onMovieClick(topRatedMovies.id) }
                                                )
                                        )
                                        Text(
                                            text = topRatedMovies.title,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.titleMedium,
                                            modifier = Modifier.padding(top = 10.dp),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(
                        text = "Now Playing",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    when(val nowplayMov = nowPlayingMovies){
                        is NowPlayMovieUiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is NowPlayMovieUiState.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = nowplayMov.message,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                        is NowPlayMovieUiState.Success -> {
                            val nowPlayMov = nowplayMov.nowPlayMovie
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(
                                    items = nowPlayMov,
                                    key = { it.id }
                                ) { movie ->
                                    Card(
                                        modifier = Modifier
                                            .width(220.dp)
                                            .clickable{ onMovieClick(movie.id) },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                                        )
                                    ) {
                                        Column {
                                            AsyncImage(
                                                model = movie.backdropUrl,
                                                contentDescription = movie.title,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(130.dp)
                                            )
                                            Column(
                                                modifier = Modifier.padding(12.dp)
                                            ) {
                                                Text(
                                                    text = movie.title,
                                                    fontSize = 15.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.titleMedium,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = movie.overview,
                                                    fontSize = 12.sp,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis // add ...
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onMovieClick = {},
        onSearchClick = {},
        onFavoriteClick = {}
    )
}
