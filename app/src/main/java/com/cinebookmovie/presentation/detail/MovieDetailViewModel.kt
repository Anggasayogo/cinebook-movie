package com.cinebookmovie.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinebookmovie.data.local.entity.FavoriteMovieEntity
import com.cinebookmovie.domain.model.DetailMovie
import com.cinebookmovie.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(val movie: DetailMovie) : DetailUiState
    data class Error(val message: String) : DetailUiState
}

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel()  {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    // apakah _isFavorite film ini sudah ada di favorit atau belum
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val result = repository.getDetailMovies(movieId)
                _uiState.value = DetailUiState.Success(result)

                // 2. Pantau status favorit dari Room Database
                checkFavoriteStatus(movieId)
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.localizedMessage ?: "Terjadi kesalahan")
            }
        }
    }

    // Fungsi memantau status favorit secara realtime
    private fun checkFavoriteStatus(movieId: Int) {
        viewModelScope.launch {
            repository.isFavorite(movieId).collect { isFav ->
                _isFavorite.value = isFav
            }
        }
    }

    // Fungsi Toggle (Tambah / Hapus Favorit)
    fun toggleFavorite(movie: FavoriteMovieEntity) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                // Jika sudah favorit, maka hapus
                repository.deleteFavoriteMovie(movie)
            } else {
                // Jika belum favorit, maka tambahkan
                repository.insertFavoriteMovie(movie)
            }
        }
    }

}