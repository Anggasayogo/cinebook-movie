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

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    // 1. State Flow for Popular Movies
    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies.asStateFlow()

    // 1. State Flow for Popular Movies
    private val _nowPlayingMovies = MutableStateFlow<List<NowPlayingMovie>>(emptyList())
    val nowPlayingMovies: StateFlow<List<NowPlayingMovie>> = _nowPlayingMovies.asStateFlow()


    // 2. State Flow for Popular TopRatedMovie
    private val _topRatedMovies = MutableStateFlow<List<TopRatedMovie>>(emptyList())
    val topRatedMovies: StateFlow<List<TopRatedMovie>> = _topRatedMovies.asStateFlow()


    // function automaticaly exceute when ui load
    init {
        getPopularMovies()
        getTopRatedMovies()
        getNowPlayingMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            try {
                _popularMovies.value = repository.getPopularMovies()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            try {
                _nowPlayingMovies.value = repository.getNowPlayingMovies()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                _topRatedMovies.value = repository.getTopRatedMovies();
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}