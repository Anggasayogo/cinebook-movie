package com.cinebookmovie.domain.repository

import com.cinebookmovie.data.local.entity.FavoriteMovieEntity
import com.cinebookmovie.domain.model.DetailMovie
import com.cinebookmovie.domain.model.Movie
import com.cinebookmovie.domain.model.NowPlayingMovie
import com.cinebookmovie.domain.model.TopRatedMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<NowPlayingMovie>
    suspend fun getTopRatedMovies(): List<TopRatedMovie>
    suspend fun getDetailMovies(movieId: Int): DetailMovie
    fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>>
    suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity)
    suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity)
    fun isFavorite(movieId: Int): Flow<Boolean>
}