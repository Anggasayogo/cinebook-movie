package com.cinebookmovie.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinebookmovie.domain.model.Movie
import com.cinebookmovie.domain.model.NowPlayingMovie
import com.cinebookmovie.domain.model.TopRatedMovie
import com.cinebookmovie.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PopularMovieUiState {
    data object Loading : PopularMovieUiState
    data class Success(val popularMovie: List<Movie>) : PopularMovieUiState
    data class Error(val message: String) : PopularMovieUiState
}

sealed interface NowPlayMovieUiState {
    data object Loading : NowPlayMovieUiState
    data class Success(val nowPlayMovie: List<NowPlayingMovie>) : NowPlayMovieUiState
    data class Error(val message: String) : NowPlayMovieUiState
}

sealed interface TopRatedMovieUiState {
    data object Loading : TopRatedMovieUiState
    data class Success(val topRatedMovie: List<TopRatedMovie>) : TopRatedMovieUiState
    data class Error(val message: String) : TopRatedMovieUiState
}
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    // 1. State Flow for Popular Movies
    private val _popularMovies = MutableStateFlow<PopularMovieUiState>(PopularMovieUiState.Loading)
    val popularMovies: StateFlow<PopularMovieUiState> = _popularMovies.asStateFlow()

    // 1. State Flow for Popular Movies
    private val _nowPlayingMovies = MutableStateFlow<NowPlayMovieUiState>(NowPlayMovieUiState.Loading)
    val nowPlayingMovies: StateFlow<NowPlayMovieUiState> = _nowPlayingMovies.asStateFlow()


    // 2. State Flow for Popular TopRatedMovie
    private val _topRatedMovies = MutableStateFlow<TopRatedMovieUiState>(TopRatedMovieUiState.Loading)
    val topRatedMovies: StateFlow<TopRatedMovieUiState> = _topRatedMovies.asStateFlow()


    // function automaticaly exceute when ui load
    init {
        getPopularMovies()
        getTopRatedMovies()
        getNowPlayingMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _popularMovies.value = PopularMovieUiState.Loading
            try {
                val movieList = repository.getPopularMovies()
                _popularMovies.value = PopularMovieUiState.Success(movieList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            _nowPlayingMovies.value = NowPlayMovieUiState.Loading
            try {
                val nowPlayMov = repository.getNowPlayingMovies()
                _nowPlayingMovies.value = NowPlayMovieUiState.Success(nowPlayMov)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            _topRatedMovies.value = TopRatedMovieUiState.Loading
            try {
                val topRatedMov = repository.getTopRatedMovies()
                _topRatedMovies.value = TopRatedMovieUiState.Success(topRatedMov)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}