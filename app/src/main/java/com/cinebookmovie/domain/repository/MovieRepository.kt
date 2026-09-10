package com.cinebookmovie.domain.repository

import com.cinebookmovie.domain.model.DetailMovie
import com.cinebookmovie.domain.model.Movie
import com.cinebookmovie.domain.model.NowPlayingMovie
import com.cinebookmovie.domain.model.TopRatedMovie

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<NowPlayingMovie>
    suspend fun getTopRatedMovies(): List<TopRatedMovie>
    suspend fun getDetailMovies(movieId: Int): DetailMovie
}