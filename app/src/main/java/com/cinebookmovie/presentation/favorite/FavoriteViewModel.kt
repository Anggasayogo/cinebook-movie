package com.cinebookmovie.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinebookmovie.data.local.entity.FavoriteMovieEntity
import com.cinebookmovie.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    // Mengambil list favorit secara otomatis dan ter-update secara real-time
    val favoriteMovies: StateFlow<List<FavoriteMovieEntity>> = repository.getFavoriteMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Fungsi untuk menghapus film dari favorit
    fun deleteFavorite(movie: FavoriteMovieEntity) {
        viewModelScope.launch {
            repository.deleteFavoriteMovie(movie)
        }
    }
}