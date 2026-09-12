package com.cinebookmovie.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinebookmovie.domain.model.Movie
import com.cinebookmovie.domain.model.NowPlayingMovie
import com.cinebookmovie.domain.model.TopRatedMovie
import com.cinebookmovie.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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

sealed interface ReFetchAllUiState {
    data object Loading : ReFetchAllUiState
    data object Success : ReFetchAllUiState
    data class Error(val message: String) : ReFetchAllUiState
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

    // 3. State for refetch all
    private val _reFetchAll = MutableStateFlow<ReFetchAllUiState>(ReFetchAllUiState.Success)
    val reFetchAll: StateFlow<ReFetchAllUiState> = _reFetchAll.asStateFlow()


    // function automaticaly exceute when ui load
    init {
        getPopularMovies()
        getTopRatedMovies()
        getNowPlayingMovies()
    }

    fun refetchAll() {
        viewModelScope.launch {
            _reFetchAll.value = ReFetchAllUiState.Loading
            try {
                val popularJob = async { getPopularMovies() }
                val topRatedJob = async { getTopRatedMovies() }
                val nowPlayingJob = async { getNowPlayingMovies() }

                // Tunggu sampai ketiganya selesai
                awaitAll(popularJob, topRatedJob, nowPlayingJob)

                _reFetchAll.value = ReFetchAllUiState.Success
            } catch (e: Exception) {
                e.printStackTrace()
                _reFetchAll.value = ReFetchAllUiState.Error(e.message ?: "Gagal memperbarui data")
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _popularMovies.value = PopularMovieUiState.Loading
            try {
                val movieList = repository.getPopularMovies()
                _popularMovies.value = PopularMovieUiState.Success(movieList)
            } catch (e: Exception) {
                e.printStackTrace()
                _popularMovies.value = PopularMovieUiState.Error(e.message ?: "Gagal memperbarui data")
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
                _nowPlayingMovies.value = NowPlayMovieUiState.Error(e.message ?: "Gagal memperbarui data")
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
                _topRatedMovies.value = TopRatedMovieUiState.Error(e.message ?: "Gagal memperbarui data")
            }
        }
    }
}